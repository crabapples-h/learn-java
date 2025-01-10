package cn.crabapples.common.mybatis.plus;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.example.common.jwt.JwtTokenUtils;
//import org.example.system.sysUser.entity.SysUser;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Objects;
//
/// **
// * TODO 填充字段插件
// *
// * @author 张三
// * 2024-12-26 10:33
// * e-mail 123456789@qq.com
// * qq 123456789
// * pc-name 张三
// */
//@Slf4j
//@Component
//public class AutoFillHandler implements MetaObjectHandler {
//    private final JwtTokenUtils jwtTokenUtils;
//
//
//    public AutoFillHandler(JwtTokenUtils jwtTokenUtils) {
//        this.jwtTokenUtils = jwtTokenUtils;
//    }
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("开始填充 insert 数据 ....");
//        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
//        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
//        SysUser userInfo = getUserInfo();
//        if (Objects.nonNull(userInfo)) {
//            this.strictInsertFill(metaObject, "createBy", String.class, userInfo.getUsername()); // 起始版本 3.3.0(推荐使用)
//        }
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("开始填充 update 数据 ....");
//        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
//    }
//
//    private SysUser getUserInfo() {
//        String userId = jwtTokenUtils.getUserId();
//        return SysUser.create().selectById(userId);
//    }
//}
