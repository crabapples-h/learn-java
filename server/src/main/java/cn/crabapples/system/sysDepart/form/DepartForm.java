package cn.crabapples.system.sysDepart.form;

import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * TODO 系统部门Form
 *
 * @author Ms.He
 * 2025-01-09 23:18
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Getter
@Setter
public class DepartForm extends BaseForm<SysDepart> {
    private String id;
    private String code;
    private String name;

    @Override
    public SysDepart toEntity() {
        SysDepart entity = SysDepart.create();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
