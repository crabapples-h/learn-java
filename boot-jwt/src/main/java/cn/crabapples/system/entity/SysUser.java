package cn.crabapples.system.entity;

import cn.crabapples.common.Dict;
import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
@Getter
@Setter
@Table("sys_user")
@ToString
public class SysUser extends BaseEntity {
    // id 为自增主键
    @Id(keyType = KeyType.Auto)
    private String id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 姓名
    @Dict(dictCode = "test")
    private String name;

    // 邮箱
    private String mail;

    // 电话
    private String phone;

    // 年龄
    private Integer age;

    @LastModifiedBy
    // 最后操作用户
    private String lastModifiedBy;

    @Column
    private List<String> rolesList;

    // 用户状态标记 0:正常 1:禁用
    private Integer status;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private LocalDateTime updateTime;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;
}
