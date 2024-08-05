package cn.crabapples;

import cn.crabapples.common.ResponseDTO;
import cn.crabapples.system.sysRole.entity.SysRole;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.JdbcConnection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class ApplicationTest {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/learn?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Test
    public void case1() {
        System.err.println(1);
        Assert.assertTrue(true);
    }

    @Test
    public void loadFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("private.key");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        log.info("---begin");
        for (String line = ""; line != null; line = reader.readLine()) {
            System.err.println(line);
        }
        log.info("---end");
    }

    @Test
    public void formatMenusTreeAsOOS() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:/1.txt"));
        ResponseDTO data = (ResponseDTO) objectInputStream.readObject();
        System.err.println("---begin");
        System.err.println(data.getData());
        System.err.println("---end");

    }

    public List<SysRole> loadData() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test.json");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        log.info("---begin");
        StringBuilder stringBuilder = new StringBuilder();
        for (String line = ""; line != null; line = reader.readLine()) {
            stringBuilder.append(line);
        }
        log.info("---end");
        List<SysRole> data = JSONObject.parseArray(stringBuilder.toString(), SysRole.class);
        System.err.println(data);
        return data;
    }

    public List<SysRole> loadDataForJdbc() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        JdbcConnection connection = (JdbcConnection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.err.println(connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from sys_roles");
        while (resultSet.next()) {
            System.err.println(resultSet);
        }
        return null;
    }

    @Test
    public void formatMenusTreeAsFile() throws SQLException, ClassNotFoundException {
        List<SysRole> roles = loadDataForJdbc();
//        roles.forEach(e -> {
//            System.err.println(e);
//        });
    }
}
