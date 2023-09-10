package cn.crabapples.system.form;

import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.entity.SysDictItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
public class DictItemForm extends BaseForm<SysDictItem> {
    private String id;
    private String dictCode;
    private String code;
    private String value;

    @Override
    public SysDictItem toEntity() {
        SysDictItem entity = SysDictItem.create();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
