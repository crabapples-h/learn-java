package cn.crabapples.system.form;

import cn.crabapples.common.Groups;
import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.entity.SysDict;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

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
@ApiModel
public class DictForm extends BaseForm<SysDict> {
    private String id;

    private Integer sort;

    @NotBlank(message = "字典名称不能为空", groups = {Groups.IsAdd.class})
    private String name;

    @NotBlank(message = "字典编码不能为空", groups = {Groups.IsAdd.class})
    private String code;

    @Override
    public SysDict toEntity() {
        SysDict entity = new SysDict();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
