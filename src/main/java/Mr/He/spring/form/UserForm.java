package Mr.He.spring.form;

import Mr.He.spring.common.groups.IsAdd;
import Mr.He.spring.common.groups.IsEdit;
import Mr.He.spring.entity.User;
import lombok.Data;

import javax.persistence.Column;
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
public class UserForm {
    @NotBlank(message = "id不能为空", groups = IsEdit.class)
    @Null(message = "id必须为空", groups = IsAdd.class)
    private String id;

    /**
     * Column length=30 数据字段长度为30
     */
    @Column(length = 30)
    @NotBlank(message = "姓名不能为空", groups = {IsAdd.class,IsEdit.class})
    private String name;

    /**
     * Column nullable = false 数据字段不能为空
     */
    @Column(columnDefinition = "int (3) default 0 not null")
    @NotNull(message = "年龄不能为空", groups = {IsAdd.class,IsEdit.class})
    private Integer age;

    public User toEntity(){
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setAge(this.age);
        return user;
    }
}
