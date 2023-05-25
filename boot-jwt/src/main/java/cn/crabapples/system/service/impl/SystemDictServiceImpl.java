package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.DictDAO;
import cn.crabapples.system.dao.DictItemDAO;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemDictService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 用户相关服务实现类[用户]
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
@Slf4j
public class SystemDictServiceImpl implements SystemDictService {
    private final DictDAO dictDAO;
    private final DictItemDAO dictItemDAO;

    public SystemDictServiceImpl(DictDAO dictDAO, DictItemDAO dictItemDAO) {
        this.dictDAO = dictDAO;
        this.dictItemDAO = dictItemDAO;
    }

    @Override
    public Iterable<SysDict> queryList(DictForm form) {
        PageRequest page = getJpaPage(form);
        return dictDAO.queryList(form, page);
    }

    @Override
    public void save(DictForm form) {
        String id = form.getId();
        if (StringUtils.isEmpty(id)) {
            dictDAO.save(form);
        } else {
            dictDAO.save(form);
        }
    }

    @Override
    public void deleteById(String id) {
        dictDAO.deleteById(id);
    }

    @Override
    public SysDict getById(String id) {
        return dictDAO.getById(id);
    }
}
