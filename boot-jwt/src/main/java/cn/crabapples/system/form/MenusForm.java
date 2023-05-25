package cn.crabapples.system.form;

import cn.crabapples.common.Groups;
import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.entity.SysMenu;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO 用户菜单Form
 *
 * @author Mr.He
 * 2021/4/12 15:42
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Getter
@Setter
public class MenusForm extends BaseForm<SysMenu> {

    private String id;

    private Integer sort;

    private String icon;

    @NotBlank(message = "菜单名称不能为空", groups = {Groups.IsAdd.class, Groups.IsEdit.class})
    private String name;

    private String url;

    /**
     * 菜单类型 1:目录 2:菜单 3:外部链接 4:按钮
     */
    @NotNull(message = "菜单类型不能为空", groups = {Groups.IsAdd.class, Groups.IsEdit.class})
    private Integer menusType;

    private String componentPath;

    private String pid;

    private Integer delFlag;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public SysMenu toEntity() {
        SysMenu entity = new SysMenu();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
