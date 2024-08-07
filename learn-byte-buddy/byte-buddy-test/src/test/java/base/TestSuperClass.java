package base;

public class TestSuperClass {

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

}
