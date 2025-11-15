package cn.crabapples.common.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseForm<T extends BaseEntity<T>> {
    protected String id;
    protected Integer pageIndex;
    protected Integer pageSize;

    public abstract T toEntity();
}
