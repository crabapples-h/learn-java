package demo.mybatis;

import java.util.List;
import java.util.Map;

public interface MyBatisMapper {
    Map<String, String> selectOneMap();

    Idcard selectOne();

    List<Idcard> selectAll();

    List<Idcard> selectAll(int start, int end);

    Long count();

    Long insert(List<Idcard> idcard);
}
