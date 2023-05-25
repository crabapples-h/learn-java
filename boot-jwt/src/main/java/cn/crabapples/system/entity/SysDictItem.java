package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
public class SysDictItem extends BaseEntity {
    @Column(columnDefinition = "varchar(64) not null comment '名称'")
    private String name;
    @Column(columnDefinition = "varchar(64) not null comment '值'")
    private String value;
    @Column(columnDefinition = "varchar(64) not null comment '字典编码'")
    private String dictCode;
}
