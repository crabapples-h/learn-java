package cn.crabapples.system.sysDict.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Table(value = "sys_dict_item")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDictItem extends BaseEntity<SysDictItem> {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    // 名称
    private String text;
    // 值
    private String value;
    // 字典编码
    private String dictCode;


    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()")
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private Date updateTime;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;

}
