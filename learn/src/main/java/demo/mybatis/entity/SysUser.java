package demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * TODO 用户实体类
 *
 * @author Mr.He
 * 2019/7/4 14:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Accessors(chain = true)
@TableName(value = "sys_user")
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> implements Serializable, Cloneable {
    @TableId
    private String id;
    private String username; // 用户名
    private String password; // 密码
    private String name;// 姓名
    //    @CMask(CMaskEnum.EMAIL)
    private String mail;// 邮箱
    //    @CMask(CMaskEnum.PHONE)
    private String phone; // 电话
    private String avatar;// 头像
    private Integer age;// 年龄
    //    @Dict(dictCode = "gender")
    private Integer gender; // 性别

    @TableField(exist = false)
//    @JoinField(
//            table = "sys_user_roles",
//            primaryKey = "id",
//            foreignKey = "user_id",
//            selectFields = {"id", "name"},
//            alias = "roleList")
    private List<String> roleList;

//    @RelationOneToMany(joinTable = "sys_user_roles",
//            joinSelfColumn = "user_id", joinTargetColumn = "role_id",
//            selfField = "id", targetField = "id")
//    @JSONField(serialize = false)
//    private List<SysRole> roleListObj;

    // 用户状态标记 0:正常 1:禁用
    private Integer status;

    // 删除标记 (0:正常 1:删除)
    @TableLogic(delval = "1", value = "0")
    private Integer delFlag;

    // 租户, 多个用逗号隔开
    private String tenantId;


//    public List<String> getRoleList() {
//        if (CollectionUtil.isEmpty(this.roleListObj)) {
//            return Collections.emptyList();
//        }
//        return roleListObj.stream().map(SysRole::getId).collect(Collectors.toList());
//    }
}
