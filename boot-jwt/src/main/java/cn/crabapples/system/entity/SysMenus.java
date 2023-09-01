package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
public class SysMenus extends BaseEntity {
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

    // 菜单类型 1:目录 2:菜单 3:超链接 4:按钮
    private Integer menusType;

    // 是否为跟目录 0:是 1:否
    private Integer isRoot;

    // 浏览器访问路径(url)
    private String path;

    // 文件存放路径
    private String filePath;

    // 授权标识
    private String permission;

    @Column(ignore = true)
    private List<SysMenus> children;

    @JSONField(serialize = false)
    private Boolean showFlag;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
