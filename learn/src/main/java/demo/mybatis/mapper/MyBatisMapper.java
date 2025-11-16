package demo.mybatis.mapper;

import demo.mybatis.entity.Idcard;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface MyBatisMapper {
    @MapKey("id")
    Map<String, String> selectOneMap();

    Idcard selectOne();

    List<Idcard> selectAll();

    List<Idcard> selectAll(int start, int end);

    long count();

    Long insert(List<Idcard> idcard);
}
