package cn.crabapples.system.sysMenu.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Table(value = "sys_menu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysMenu extends BaseEntity<SysMenu> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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

    @Column(ignore = true)
    private List<SysMenu> children;

    private Integer showFlag;

    //创建人
    @JSONField(serialize = false)
    private String createBy;

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @JSONField(serialize = false)
    private Integer delFlag;
}
