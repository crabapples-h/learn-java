package cn.crabapples.spring.service;

import cn.crabapples.spring.entity.MenuInfo;

import java.util.List;


/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:44
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface MenuInfoService {
    List<MenuInfo> findAll();

    List<MenuInfo> findByListId(String id);
}
