package Mr.He.spring.dao;

import Mr.He.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * TODO JPA中SQL和HQL演示
 * 
 * @author Mr.He
 * 2019/7/4 1422:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    List<User> findByNameLike(String name);

    /**
     * 使用hql查询
     * @param name @Param里的name
     * @return 查询到的结果集
     */
    @Query("from User user where user.name=:name")
    List<User> findByHQL(@Param("name") String name);

    /**
     * 使用sql查询
     * @param name  ?1表示第一个参数
     * @param age   ?2表示第二个参数
     * @return 查询到的结果集
     */
    @Query(value = "select * from user where name = ?1 and age = ?2", nativeQuery = true)
    List<User> findBySQL(String name, Integer age);
}
