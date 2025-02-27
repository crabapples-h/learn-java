package cn.crabapples.system.sysDict.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import cn.crabapples.common.mybatis.flex.OnInsertListener;
import cn.crabapples.common.mybatis.flex.OnUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.*;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Table(value = "sys_dict_item", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDictItem extends BaseEntity<SysDictItem> {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String text; // 名称
    private String value; // 值
    private String dictCode; // 字典编码

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

}
