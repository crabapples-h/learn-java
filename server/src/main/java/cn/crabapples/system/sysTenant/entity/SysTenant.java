package cn.crabapples.system.sysTenant.entity;

import cn.crabapples.common.base.BaseEntity;
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
@Table(value = "sys_tenant", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysTenant extends BaseEntity<SysTenant> {
    // id 为自增主键
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String name;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    private Integer delFlag;

}
