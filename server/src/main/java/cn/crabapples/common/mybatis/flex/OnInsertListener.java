package cn.crabapples.common.mybatis.flex;

import cn.crabapples.common.base.BaseEntity;
import com.mybatisflex.annotation.InsertListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * TODO 新建时填充字段
 *
 * @author Ms.He
 * 2025-01-10 09:21
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Slf4j
@Component
public class OnInsertListener implements InsertListener {

    @Override
    public void onInsert(Object o) {
        BaseEntity<?> baseEntity = (BaseEntity<?>) o;
        baseEntity.setCreateTime(LocalDateTime.now());
        baseEntity.setUpdateTime(LocalDateTime.now());
//        baseEntity.setCreateBy("");
    }
}
