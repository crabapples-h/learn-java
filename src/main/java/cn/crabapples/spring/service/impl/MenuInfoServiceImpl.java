package cn.crabapples.spring.service.impl;

import cn.crabapples.spring.dao.MenuInfoRepository;
import cn.crabapples.spring.dao.MenuListRepository;
import cn.crabapples.spring.entity.MenuInfo;
import cn.crabapples.spring.service.MenuInfoService;
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
public class MenuInfoServiceImpl implements MenuInfoService {
    private final MenuInfoRepository menuInfoRepository;

    public MenuInfoServiceImpl(MenuInfoRepository menuInfoRepository) {
        this.menuInfoRepository = menuInfoRepository;
    }

    @Override
    public List<MenuInfo> findAll() {
        return menuInfoRepository.findAll();
    }

    @Override
    public List<MenuInfo> findByListId(String id) {
        return menuInfoRepository.findByListId(id);
    }
}
