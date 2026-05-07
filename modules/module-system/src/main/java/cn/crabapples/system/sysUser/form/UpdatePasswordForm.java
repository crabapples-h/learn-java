package cn.crabapples.system.sysUser.form;

import cn.crabapples.common.utils.Groups;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePasswordForm extends ResetPasswordForm{
    @Length(max = 16, message = "长度错误", groups = {Groups.IsCheckLength.class})
        @NotBlank(message = "密码不能为空", groups = {Groups.IsNotNull.class})
        private String oldPassword;
}
