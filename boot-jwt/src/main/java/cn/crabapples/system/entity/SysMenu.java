package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mybatisflex.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO 系统菜单
 *
 * @author Mr.He
 * 3/1/20 11:58 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Table(value = "sys_menu", camelToUnderline = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysMenu extends BaseEntity<SysMenu> {
    // id 为自增主键
    @Id(keyType = KeyType.Auto)
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

    //    @Column(ignore = true)
    @RelationOneToMany(selfField = "id", targetField = "pid")
    private List<SysMenu> children;

    private Integer showFlag;

    //创建人
    @CreatedBy
    private String createBy;

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

    /*
     * issues 154
     * 当字段类型被标记为 isLogicDelete ,且为Integer类型时会变成null
     */
    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true, jdbcType = JdbcType.INTEGER)
    @JSONField(serialize = false)
    private int delFlag;
}
