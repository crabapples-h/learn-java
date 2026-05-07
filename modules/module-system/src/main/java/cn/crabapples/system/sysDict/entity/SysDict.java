package cn.crabapples.system.sysDict.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@TableName(value = "sys_dict")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDict extends BaseEntity<SysDict> {
    // id 为自增主键
    @Id
    private String id;
    private Integer sort;
    private String code;
    private String name;

    // 删除标记 (0:正常 1:删除)
    @TableLogic(delval = "1", value = "0")
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

}
