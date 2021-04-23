package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.MenusRepository;
import cn.crabapples.system.entity.SysMenus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * TODO 系统菜单DAO
 *
 * @author Mr.He
 * 2021/4/12 18:08
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class MenusDAO extends BaseDAO {
    private final MenusRepository menusRepository;

    public MenusDAO(MenusRepository menusRepository) {
        this.menusRepository = menusRepository;
    }

    public long count() {
        return menusRepository.countByDelFlagAndIsRoot(DIC.NOT_DEL, DIC.IS_ROOT);
    }

    public SysMenus findById(String id) {
        Optional<SysMenus> optional = menusRepository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }


    public SysMenus save(SysMenus entity) {
        return menusRepository.save(entity);
    }

    public Page<SysMenus> findRoot(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_SORT);
        return menusRepository.findByDelFlagAndIsRoot(pageable, DIC.NOT_DEL, DIC.IS_ROOT);
    }

    public List<SysMenus> findRoot() {
        return menusRepository.findByDelFlagAndIsRoot(DIC.NOT_DEL, DIC.IS_ROOT);
    }

    public List<SysMenus> findByParentId(String id) {
        throw new ApplicationException("暂未实现");
    }

    public List<SysMenus> findByIds(List<String> ids) {
        return menusRepository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysMenus> findRootsByIds(List<String> ids) {
        return menusRepository.findByDelFlagAndIsRootAndIdIn(DIC.NOT_DEL, DIC.IS_ROOT, ids);
    }
    public List<SysMenus> findButtonsByIds(List<String> ids) {
        return menusRepository.findByDelFlagAndIdInAndMenusType(DIC.NOT_DEL, ids,DIC.MENUS_TYPE_BUTTON);
    }
}
