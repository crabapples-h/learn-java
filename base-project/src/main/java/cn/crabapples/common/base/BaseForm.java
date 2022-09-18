package cn.crabapples.common.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseForm<T extends BaseEntity> {
    protected String id;
    protected int pageIndex;
    protected int pageSize;
    protected LocalDateTime createTime;

    public abstract T toEntity();
}
