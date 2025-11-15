//package cn.crabapples.common.mybatis.flex;
//
//import cn.crabapples.common.base.BaseEntity;
//import com.mybatisflex.annotation.UpdateListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
///**
// * TODO 更新时填充字段
// *
// * @author Ms.He
// * 2025-01-10 09:21
// * e-mail crabapples.cn@gmail.com
// * qq 294046317
// * pc-name mshe
// */
//@Slf4j
//@Component
//public class OnUpdateListener implements UpdateListener {
//    @Override
//    public void onUpdate(Object o) {
//        BaseEntity<?> baseEntity = (BaseEntity<?>) o;
//        baseEntity.setUpdateTime(LocalDateTime.now());
////        baseEntity.setUpdateBy("");
//    }
//}
