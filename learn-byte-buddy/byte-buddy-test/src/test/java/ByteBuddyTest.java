import base.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

public class ByteBuddyTest {
    private String path;

    @Before
    public void before() {
        path = Objects.requireNonNull(this.getClass().getResource("")).getPath();
    }

    /**
     * 生成一个类
     */
    @Test
    public void caseTest() throws IOException {
        NamingStrategy.SuffixingRandom test = new NamingStrategy.SuffixingRandom("XXXX");
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                // 关闭生成的字节码进行合法校验
//                .with(TypeValidation.of(false))
                /*
                 * 默认生成的类的命名规则
                 * 在不指定命名策略的情况下：
                 *  1. 对于父类是jdk自带的情况下net.bytebuddy.renamed.java.Lang.Object$ByteBuddy$xxxx
                 *  2. 对于父类不是jdk自带情况下的类，则包名为当前包名$父类名$xxxx
                 *  在指定命名了策略的情况下：
                 *  1. 对于父类是jdk自带的情况下net.bytebuddy.renamed.java.Lang.Object$XXXX$xxxx
                 *  2. 对于父类不是jdk自带情况下的类，则包名为当前包名$XXXX$xxxx
                 */
                // 指定生成的类的命名策略
                .with(test)
                // 指定父类
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                .make();

        byte[] bytes = unloaded.getBytes();
        FileUtils.writeByteArrayToFile(new File(path + "base.TestSuperClass.class"), bytes);
        unloaded.saveIn(new File(path));
        // 把生成的字节码文件直接注入到Jar包里
//        unloaded.inject(new File(path + "test.jar"));
    }

    /**
     * 对实例方法进行插桩
     * 插桩: 对字节码的修改或者增强
     */
    @Test
    public void caseTest2() throws IOException, InstantiationException, IllegalAccessException {
        NamingStrategy.SuffixingRandom test = new NamingStrategy.SuffixingRandom("XXXX");
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .with(test)
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // named通过名字指定需要拦截的方法
                .method(named("toString"))
                // 指定拦截到方法后的处理方法
                .intercept(FixedValue.value("hello kitty"))
                .make();
        // unloaded中有的方法在loaded中也有相同的方法
        DynamicType.Loaded<TestSuperClass> loaded = unloaded.load(getClass().getClassLoader());
        ClassLoader classLoader = loaded.getClass().getClassLoader();
        System.err.println(classLoader);
        System.out.println(loaded.getLoaded().newInstance());
        unloaded.saveIn(new File(path));
    }

    /**
     * 动态增强的三种方式
     * subclass
     */
    @Test
    public void caseTest3() throws IOException, InstantiationException, IllegalAccessException {
        NamingStrategy.SuffixingRandom test = new NamingStrategy.SuffixingRandom("XXXX");
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .with(test)
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // named通过名字指定需要拦截的方法
                .method(named("selectUserName")
                        // named通过返回值类型拦截
                        .and(returns(TypeDescription.CLASS)
                                .or(returns(TypeDescription.OBJECT))
                                .or(returns(TypeDescription.ForLoadedType.of(String.class)))
                        )
                )
                // 指定拦截到方法后的处理方法
                .intercept(FixedValue.nullValue())
                .make();
        // unloaded中有的方法在loaded中也有相同的方法
        DynamicType.Loaded<TestSuperClass> loaded = unloaded.load(getClass().getClassLoader());
        ClassLoader classLoader = loaded.getClass().getClassLoader();
        System.err.println(classLoader);
        System.out.println(loaded.getLoaded().newInstance().selectUserName("123"));
        unloaded.saveIn(new File(path));
    }

    /**
     * 动态增强的三种方式
     * rebase 变基。保留原方法，并重命名为xx$original(),xx为拦截后的逻辑
     */
    @Test
    public void caseTest4() throws IOException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .rebase(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // named通过名字指定需要拦截的方法
                .method(named("selectAge"))
                // 指定拦截到方法后的处理方法
                .intercept(FixedValue.value(-1))
                .make();
        unloaded.saveIn(new File(path));
    }

    /**
     * 动态增强的三种方式
     * redefine 变基。原方法不再保留，新方法为拦截后的逻辑
     */
    @Test
    public void caseTest5() throws IOException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .redefine(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // named通过名字指定需要拦截的方法
                .method(named("selectAge"))
                // 指定拦截到方法后的处理方法
                .intercept(FixedValue.value(-1))
                .make();
        unloaded.saveIn(new File(path));
    }

    /**
     * 插入新方法
     */
    @Test
    public void caseTest6() throws IOException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .rebase(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // 插入新方法 方法名,返回值类型,限定词
                .defineMethod("method1",
                        TypeDescription.ForLoadedType.of(String.class),
                        Modifier.PUBLIC + Modifier.STATIC)
                // 指定方法参数 参数类型,参数名
                .withParameter(TypeDescription.ForLoadedType.of(String.class), "arg")
                // 返回值
                .intercept(FixedValue.value("新方法"))
                .make();
        unloaded.saveIn(new File(path));
    }

    /**
     * 插入新属性
     */
    @Test
    public void caseTest7() throws IOException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .rebase(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                // 插入新属性 属性名,类型,限定词
                .defineField("field1", TypeDescription.ForLoadedType.of(String.class),
                        Modifier.PRIVATE)
                // 定义get,set方法,实现接口
                .implement(TestFieldInterface.class)
                .intercept(FieldAccessor.ofField("field1"))
                .make();
        unloaded.saveIn(new File(path));
    }

    /**
     * 方法委托(委托给静态方法)
     */
    @Test
    public void caseTest8() throws IOException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                .method(named("testMethod"))
                // 把处理逻辑委托给MethodIntercept1中与被拦截方法同签名的静态方法
                .intercept(MethodDelegation.to(MethodIntercept1.class))
                .make();
        unloaded.saveIn(new File(path));
    }

    /**
     * 方法委托(委托给实例方法)
     */
    @Test
    public void caseTest9() throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                .method(named("testMethod"))
                // 把处理逻辑委托给MethodIntercept2中与被拦截方法同签名的实例方法
                .intercept(MethodDelegation.to(new MethodIntercept2()))
                .make();
        DynamicType.Loaded<TestSuperClass> load = unloaded.load(getClass().getClassLoader());
        Class<? extends TestSuperClass> loaded = load.getLoaded();
        TestSuperClass testSuperClass = loaded.getDeclaredConstructor().newInstance();
        testSuperClass.testMethod();
        load.saveIn(new File(path));
    }

    /**
     * 方法委托(委托给带有@RuntimeType注解的方法)
     */
    @Test
    public void caseTest10() throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
                .subclass(TestSuperClass.class)
                .name("cn.crabapples.TestSubClass")
                .method(named("testMethod"))
                // 把处理逻辑委托给MethodIntercept2中与被拦截方法同签名的实例方法
                .intercept(MethodDelegation.to(new MethodIntercept3()))
                .make();
        DynamicType.Loaded<TestSuperClass> load = unloaded.load(getClass().getClassLoader());
        Class<? extends TestSuperClass> loaded = load.getLoaded();
        TestSuperClass testSuperClass = loaded.getDeclaredConstructor().newInstance();
        Object o = testSuperClass.testMethod();
        System.err.println(o);
        load.saveIn(new File(path));
    }
}
