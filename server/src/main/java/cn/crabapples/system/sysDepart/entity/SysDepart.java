package cn.crabapples.system.sysDepart.entity;

import cn.crabapples.common.base.BaseEntity;
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
@Table(value = "sys_depart")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDepart extends BaseEntity<SysDepart> {
    // id 为自增主键
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String code;
    private String name;

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    private Integer delFlag;

    //创建人
    private String createBy;

    //更新人
    private String updateBy;

}
