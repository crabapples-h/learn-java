```java
@Test
public void caseTest() throws IOException {
    // 创建命名规则
NamingStrategy.SuffixingRandom test = new NamingStrategy.SuffixingRandom("XXXX");
DynamicType.Unloaded<TestSuperClass> unloaded = new ByteBuddy()
// 关闭生成的字节码进行合法校验
 .with(TypeValidation.of(false))
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
FileUtils.writeByteArrayToFile(new File(path + "TestSuperClass.class"), bytes);
unloaded.saveIn(new File(path));
// 把生成的字节码文件直接注入到Jar包里
unloaded.inject(new File(path + "test.jar"));
}
```
- 

