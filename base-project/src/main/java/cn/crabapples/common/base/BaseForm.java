package cn.crabapples.common.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseForm {
    protected String id;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;
    protected int delFlag;
    protected int pageIndex;
    protected int pageSize;
}
