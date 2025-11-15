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
@TableName(value = "sys_dict_item")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDictItem extends BaseEntity<SysDictItem> {
    @Id
    private String id;
    private String text; // 名称
    private String value; // 值
    private String dictCode; // 字典编码

    // 删除标记 (0:正常 1:删除)
    @TableLogic(value = "0", delval = "1")
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

}
