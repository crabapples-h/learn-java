package cn.crabapples.system.form;

import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.entity.SysDict;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * TODO 系统字典项Form
 *
 * @author Ms.He
 * 2023/5/18 20:22
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Getter
@Setter
public class DictForm extends BaseForm<SysDict> {
    private String id;
    private Integer sort;
    private String code;
    private String name;

    @Override
    public SysDict toEntity() {
        SysDict entity = SysDict.create();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
