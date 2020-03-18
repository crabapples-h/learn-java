package cn.crabapples.spring.service;

import cn.crabapples.spring.entity.MenuList;
import cn.crabapples.spring.entity.SysUser;
import cn.crabapples.spring.form.UserForm;

import java.util.List;
import java.util.Optional;


/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:44
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface MenuListService {
    List<MenuList> findAll();
}
