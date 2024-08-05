package cn.crabapples.system.sysDict.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@TableName("sys_dict")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDict extends BaseEntity<SysDict> {
    // id 为自增主键
    @TableId(type = ASSIGN_UUID)
    private String id;
    private Integer sort;
    private String code;
    private String name;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;

}
