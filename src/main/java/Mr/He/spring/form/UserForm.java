package Mr.He.spring.form;

import Mr.He.spring.common.groups.*;
import Mr.He.spring.entity.User;
import Mr.He.spring.groups.IsNotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.*;

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
public class UserForm {
    @NotBlank(message = "id不能为空", groups = IsEdit.class)
    @NotBlank(message = "id不能为空", groups = IsStatusChange.class)
    @Null(message = "id必须为空", groups = IsAdd.class)
    private String id;

    @Length(max = 32 ,message = "长度错误",groups = {IsCheckLength.class, IsLogin.class})
    @NotBlank(message = "用户名不能为空", groups = {IsNotNull.class, IsLogin.class})
    private String username;

    @Length(max = 32 ,message = "长度错误",groups = {IsCheckLength.class, IsLogin.class})
    @NotBlank(message = "密码不能为空", groups = {IsNotNull.class, IsLogin.class})
    private String password;

    @Length(max = 32)
    @NotBlank(message = "姓名不能为空", groups = {IsAdd.class,IsEdit.class})
    private String name;

    @Length(max = 3)
    @NotNull(message = "年龄不能为空", groups = {IsAdd.class,IsEdit.class})
    private Integer age;

    public User toEntity(){
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setAge(this.age);
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }
}
