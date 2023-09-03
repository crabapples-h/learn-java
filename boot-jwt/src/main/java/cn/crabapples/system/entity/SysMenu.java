package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * TODO 系统菜单
 *
 * @author Mr.He
 * 3/1/20 11:58 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@TableName(value = "sys_menu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysMenu extends BaseEntity<SysMenu> {
    @TableId(type = ASSIGN_UUID)
    private String id;

    private String pid;
    // 排序
    private Integer sort;

    // 菜单图标
    private String icon;
    // 菜单名
    private String name;

    // 超链接地址
    private String link;

    private Integer menusType;

    // 浏览器访问路径(url)
    private String path;

    // 文件存放路径
    private String filePath;

    // 授权标识
    private String permission;

    @TableField(exist = false)
    private List<SysMenu> children;

    private Integer showFlag;

    //创建人
    @CreatedBy
    private String createBy;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    @JSONField(serialize = false)
    private Integer delFlag;
}
