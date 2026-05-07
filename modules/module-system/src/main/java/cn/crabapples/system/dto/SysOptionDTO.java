package cn.crabapples.system.dto;

import cn.crabapples.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SysOptionDTO extends BaseEntity<SysOptionDTO> {
    private String id;
    private String label;
    private String value;
}
