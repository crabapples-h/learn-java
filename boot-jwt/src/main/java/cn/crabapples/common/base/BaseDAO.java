package cn.crabapples.common.base;

import cn.crabapples.common.ApplicationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * TODO 数据库查询基本字段
 *
 * @author Mr.He
 * 2021/4/12 3:27
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public abstract class BaseDAO {
    protected final static Sort ASC_CREATE_TIME = Sort.by("createTime").ascending();
    protected final static Sort ASC_SORT = Sort.by("sort").ascending();

    protected void notFoundItem() {
        throw new ApplicationException("找不到对应数据");
    }
}
