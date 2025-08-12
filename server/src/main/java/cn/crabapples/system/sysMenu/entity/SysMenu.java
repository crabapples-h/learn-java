package cn.crabapples.system.sysMenu.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.mybatis.flex.OnInsertListener;
import cn.crabapples.common.mybatis.flex.OnUpdateListener;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Collections;
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
@Table(value = "sys_menu", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysMenu extends BaseEntity<SysMenu> {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String pid;
    private Integer sort; // 排序
    private String icon;  // 菜单图标
    private String name; // 菜单名
    private String link; // 超链接地址
    private Integer menusType;
    private String path; // 浏览器访问路径(url)
    private String filePath; // VUE文件存放路径
    private String permission; // 授权标识

    @Column(ignore = true)
    private List<SysMenu> children = Collections.EMPTY_LIST;

    private Integer showFlag;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @JSONField(serialize = false)
    private Integer delFlag;
}
