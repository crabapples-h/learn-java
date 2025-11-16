package demo.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import demo.mybatis.entity.Idcard;
import demo.mybatis.entity.SysUser;
import demo.mybatis.mapper.MyBatisMapper;
import demo.mybatis.mapper.SysUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyBatis {
    private static final String URL1 = "jdbc:mysql://localhost:3306/id_card_2000w";
    private static final String SQL = "select * from id_card_simple";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final Logger log = LoggerFactory.getLogger(MyBatis.class);
    private static SqlSession session;

    private static void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void queryUser() {
        SysUserMapper mapper = session.getMapper(SysUserMapper.class);
        List<SysUser> sysUsers = mapper.selectList(new LambdaQueryWrapper<>());
        for (SysUser sysUser : sysUsers) {
            log.info("用户:[{}]", sysUser);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        init();
        queryUser();
    }


    private static void insertData() {
        MyBatisMapper mapper = session.getMapper(MyBatisMapper.class);
        long count = mapper.count();
        long times = count / 50000;
        long start1 = System.currentTimeMillis();
        for (int time = 0; time < times; time++) {
            long start = System.currentTimeMillis();
            List<Idcard> newIdcardList = new ArrayList<>(50000);
            List<Idcard> queryData = mapper.selectAll(time * 50000, 50000);
            for (int i1 = 0; i1 < queryData.size(); i1++) {
                Idcard idcard1 = queryData.get(i1);
                for (int j1 = i1; j1 < queryData.size(); j1++) {
                    Idcard idcard2 = queryData.get(j1);
                    if (Objects.equals(idcard1.getId(), idcard2.getId())) {
                        break;
                    }
                }
                String email = String.valueOf(idcard1.getEmail());
                idcard1.setEmail(email.trim());
                newIdcardList.add(idcard1);
            }
            mapper.insert(newIdcardList);
            session.commit();
            session.clearCache();
            long end = System.currentTimeMillis();
            log.info("第[{}]次循环,添加数据:[{}]条,耗时:[{}]ms", time, newIdcardList.size(), end - start);
        }
        long end1 = System.currentTimeMillis();
        log.info("添加数据完成,共耗时:[{}]s", (end1 - start1) / 1000);
    }
}
