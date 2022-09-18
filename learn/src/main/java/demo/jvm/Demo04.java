package demo.jvm;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Getter
    @Setter
    @ToString
    static class School {
        private String name;
        private List<Person> people;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    static class Person {
        private String name;
        private School school;
    }
}
