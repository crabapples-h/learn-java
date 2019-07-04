package Mr.He.spring.dao;

import Mr.He.spring.entity.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author wishforyou.xia@gmail.com
 * @date 2019/7/4 14:51
 */
public interface DemoUserDao extends JpaRepository<DemoUser, Serializable> {
    DemoUser findByName(String name);

    DemoUser findByNameAndAge(String name, Integer age);

    List<DemoUser> findByNameLike(String name);

    /**
     * 使用hql查询
     * @param name @Param里的name
     * @return
     */
    @Query("from DemoUser u where u.name=:name")
    DemoUser findByHQL(@Param("name") String name);

    /**
     * 使用sql查询
     * @param name  ?1表示第一个参数
     * @param age   ?2表示第二个参数
     * @return
     */
    @Query(value = "select * from demo_user where name = ?1 and age = ?2", nativeQuery = true)
    DemoUser findBySQL(String name, Integer age);


}
