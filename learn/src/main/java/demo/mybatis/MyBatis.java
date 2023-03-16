package demo.mybatis;


import com.mchange.v1.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class MyBatis {
    private static final String URL1 = "jdbc:mysql://localhost:3306/id_card_2000w";
    private static final String SQL = "select * from id_card_simple";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        MyBatisMapper mapper = session.getMapper(MyBatisMapper.class);
        Long count = mapper.count();
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
