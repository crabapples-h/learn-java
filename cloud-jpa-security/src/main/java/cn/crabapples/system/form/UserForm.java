package cn.crabapples.system.form;

import cn.crabapples.common.Groups;
import cn.crabapples.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * TODO 用户信息提交Form
 *
 * @author Mr.He
 * 2020/1/27 11:26
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Data
@ApiModel("登录")
public class UserForm {
    @NotBlank(message = "id不能为空", groups = Groups.IsEdit.class)
    @Null(message = "id必须为空", groups = Groups.IsAdd.class)
    private String id;

    @Length(max = 32 ,message = "长度错误",groups = {Groups.IsCheckLength.class, Groups.IsLogin.class})
    @NotBlank(message = "用户名不能为空", groups = {Groups.IsNotNull.class, Groups.IsLogin.class})
    @ApiModelProperty(example = "111")
    private String username;

    @Length(max = 32 ,message = "长度错误",groups = {Groups.IsCheckLength.class, Groups.IsLogin.class})
    @NotBlank(message = "密码不能为空", groups = {Groups.IsNotNull.class, Groups.IsLogin.class})
    @ApiModelProperty(example = "123456")
    private String password;

    @Length(max = 32)
    @NotBlank(message = "姓名不能为空", groups = {Groups.IsAdd.class, Groups.IsEdit.class})
    private String name;

    @Length(max = 3)
    @NotNull(message = "年龄不能为空", groups = {Groups.IsAdd.class,Groups.IsEdit.class})
    private Integer age;

    public SysUser toEntity(){
        SysUser user = new SysUser();
        user.setId(this.id);
        user.setName(this.name);
        user.setAge(this.age);
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }
}
