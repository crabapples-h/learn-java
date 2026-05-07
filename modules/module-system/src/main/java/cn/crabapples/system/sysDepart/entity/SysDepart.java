package cn.crabapples.system.sysDepart.entity;

import cn.crabapples.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@TableName(value = "sys_depart")
@ToString
@Data(staticConstructor = "create")
@Accessors(chain = true)
public class SysDepart extends BaseEntity<SysDepart> {
    // id 为自增主键
    @Id
    private String id;
    private String code;
    private String name;

    // 删除标记 (0:正常 1:删除)
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;


}
