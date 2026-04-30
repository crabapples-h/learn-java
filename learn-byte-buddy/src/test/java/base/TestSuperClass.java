package base;

public class TestSuperClass {
    public TestSuperClass() {
        System.err.println("构造方法：TestSuperClass()");
    }

    public String selectUserName(String id) {
        return String.format("用户Id:[%s]", id);
    }

    public int selectAge(Integer age) {
        return age;
    }

    public String selectGander(Integer gender) {
        return String.format("用户性别:[%d]", gender);
    }

    public Object testMethod() {
        return "source Method";
    }

    public Object testMethod1(int number) {
        return "source Method1:" + number;
    }

    public static void testStaticMethod() {
        System.err.println("这是一个静态方法");
    }

}
