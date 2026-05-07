package cn.crabapples.system.sysMenu.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

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
//@Table(value = "sys_menu", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@TableName(value = "sys_menu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysMenu extends BaseEntity<SysMenu> {
    @Id
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

    @TableField(exist = false)
    private boolean hasChildren; // 是否有子菜单

    @TableField(exist = false)
    private List<SysMenu> children = Collections.EMPTY_LIST;

    private Integer showFlag;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    @JSONField(serialize = false)
    private Integer delFlag;
}
