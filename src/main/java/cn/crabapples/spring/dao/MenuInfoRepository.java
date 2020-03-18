package cn.crabapples.spring.dao;

import cn.crabapples.spring.entity.MenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface MenuInfoRepository extends JpaRepository<MenuInfo,String> {
    List<MenuInfo> findByListId(String id);
}
