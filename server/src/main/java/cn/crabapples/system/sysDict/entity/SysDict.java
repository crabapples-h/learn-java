package cn.crabapples.system.sysDict.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import cn.crabapples.common.mybatisflex.OnInsertListener;
import cn.crabapples.common.mybatisflex.OnUpdateListener;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Table(value = "sys_dict", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDict extends BaseEntity<SysDict> {
    // id 为自增主键
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private Integer sort;
    private String code;
    private String name;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

}
