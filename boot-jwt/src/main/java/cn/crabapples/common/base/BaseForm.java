package cn.crabapples.common.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseForm<T extends BaseEntity<T>> {
    protected String id;
    protected Integer pageIndex;
    protected Integer pageSize;
    protected LocalDateTime createTime;

    public abstract T toEntity();
}
