package Mr.He.spring.dao;

import Mr.He.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author wishforyou.xia@gmail.com
 * @date 2019/7/4 14:51
 */
public interface UserDao extends JpaRepository<User, Serializable> {
    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    List<User> findByNameLike(String name);

    /**
     * 使用hql查询
     * @param name @Param里的name
     * @return
     */
    @Query("from User u where u.name=:name")
    User findByHQL(@Param("name") String name);

    /**
     * 使用sql查询
     * @param name  ?1表示第一个参数
     * @param age   ?2表示第二个参数
     * @return
     */
    @Query(value = "select * from user where name = ?1 and age = ?2", nativeQuery = true)
    User findBySQL(String name, Integer age);


}
