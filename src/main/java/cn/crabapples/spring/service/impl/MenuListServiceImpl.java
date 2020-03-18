package cn.crabapples.spring.service.impl;

import cn.crabapples.spring.dao.MenuListRepository;
import cn.crabapples.spring.entity.MenuList;
import cn.crabapples.spring.service.MenuListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:46
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
public class MenuListServiceImpl implements MenuListService {
    private final MenuListRepository menuListRepository;

    public MenuListServiceImpl(MenuListRepository menuListRepository) {
        this.menuListRepository = menuListRepository;
    }

    @Override
    public List<MenuList> findAll() {
        return menuListRepository.findAll();
    }
}
