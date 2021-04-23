package cn.crabapples.system.form;

import cn.crabapples.common.Groups;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @NotBlank(message = "id不能为空", groups = {Groups.IsNotNull.class, Groups.IsEdit.class})
    private String id;

    @Length(max = 16, message = "长度错误", groups = {Groups.IsCheckLength.class, Groups.IsLogin.class})
    @NotBlank(message = "用户名不能为空", groups = {Groups.IsNotNull.class, Groups.IsLogin.class})
    @ApiModelProperty(example = "admin")
    private String username;

    @Length(max = 16, message = "长度错误", groups = {Groups.IsCheckLength.class, Groups.IsLogin.class, Groups.IsUpdatePassword.class})
    @NotBlank(message = "密码不能为空", groups = {Groups.IsNotNull.class, Groups.IsLogin.class, Groups.IsUpdatePassword.class})
    @ApiModelProperty(example = "12345678")
    private String password;

    @Length(max = 16, message = "长度错误", groups = {Groups.IsCheckLength.class,  Groups.IsUpdatePassword.class})
    @NotBlank(message = "新密码不能为空", groups = {Groups.IsResetPassword.class, Groups.IsUpdatePassword.class})
    private String newPassword;

    @Length(max = 16, message = "长度错误", groups = {Groups.IsCheckLength.class,  Groups.IsUpdatePassword.class})
    @NotBlank(message = "重复密码不能为空", groups = {Groups.IsResetPassword.class, Groups.IsUpdatePassword.class})
    private String againPassword;

    @Length(max = 16)
    @NotBlank(message = "姓名不能为空", groups = {Groups.IsNotNull.class, Groups.IsAdd.class, Groups.IsEdit.class})
    private String name;

    @NotBlank(message = "邮箱不能为空", groups = {Groups.IsNotNull.class, Groups.IsAdd.class, Groups.IsEdit.class})
    private String mail;

    @NotBlank(message = "电话不能为空", groups = {Groups.IsNotNull.class, Groups.IsAdd.class, Groups.IsEdit.class})
    private String phone;

    @Length(max = 3)
    @NotNull(message = "年龄不能为空", groups = {Groups.IsAdd.class, Groups.IsEdit.class})
    private Integer age;

    private List<String> rolesList;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}