package cn.crabapples.common;

import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.system.sysUser.entity.SysUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class MybatisPlusAutoFillHandler implements MetaObjectHandler {
    private final JwtTokenUtils jwtTokenUtils;
    private final HttpServletRequest request;

    public MybatisPlusAutoFillHandler(JwtTokenUtils jwtTokenUtils, HttpServletRequest request) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.request = request;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始填充 insert 数据 ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        SysUser userInfo = getUserInfo();
        if (Objects.nonNull(userInfo)) {
            this.strictInsertFill(metaObject, "createBy", String.class, userInfo.getUsername()); // 起始版本 3.3.0(推荐使用)
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始填充 update 数据 ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }

    private SysUser getUserInfo() {
        String userId = jwtTokenUtils.getUserId();
        return SysUser.create().selectById(userId);
    }
}
