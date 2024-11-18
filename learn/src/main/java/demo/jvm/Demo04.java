package demo.jvm;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存溢出演示(循环引用)
 *
 * @author Mr.He
 * 2022/5/28 19:14
 * pc-name mrhe
 */
public class Demo04 {
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        School school = new School();
        Person person1 = new Person("张三", school);
        Person person2 = new Person("李四", school);
        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        school.setName("第一小学");
        school.setPeople(list);
        System.out.println(JSONObject.toJSONString(school));
    }

    static class School {
        private String name;
        private List<Person> people;

        public String getName() {
            return this.name;
        }

        public List<Person> getPeople() {
            return this.people;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPeople(List<Person> people) {
            this.people = people;
        }

        public String toString() {
            return "Demo04.School(name=" + this.getName() + ", people=" + this.getPeople() + ")";
        }
    }

    static class Person {
        private String name;
        private School school;

        public Person(String name, School school) {
            this.name = name;
            this.school = school;
        }

        public String getName() {
            return this.name;
        }

        public School getSchool() {
            return this.school;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public String toString() {
            return "Demo04.Person(name=" + this.getName() + ", school=" + this.getSchool() + ")";
        }
    }
}
