package Mr.He.spring.service.impl;

import Mr.He.spring.common.ApplicationException;
import Mr.He.spring.common.utils.AesUtils;
import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;
import Mr.He.spring.service.SysService;
import Mr.He.spring.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;

/**
 * TODO 系统相关服务实现类
 *
 * @author Mr.He
 * 2020/1/28 23:23
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
public class SysServiceImpl implements SysService {

    @Value("${aesKey}")
    private String aesKey;
    private static final Logger logger = LoggerFactory.getLogger(SysServiceImpl.class);
    private UserService userService;
    private RedisT dsf;

    public SysServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String login(UserForm form) {
        try {
            String username = form.getUsername();
            String password = form.getPassword();
            for (int i = 0; i < 100; i++) {
                password = DigestUtils.md5Hex(password);
            }
            password = AesUtils.doFinal(aesKey, password, Cipher.ENCRYPT_MODE);
            logger.info("开始登录->用户名:[{}],密码:[{}]", username, password);
            User user = userService.findByUsernameAndPasswordAndStatusNotAndDelFlagNot(username, password, 1,1).orElse(null);
            if(user == null){
                throw new ApplicationException("用户名或密码错");
            }
            String token = DigestUtils.md5Hex(user.getId() + System.currentTimeMillis()).toUpperCase();
            logger.info("登录成功->token:[{}],", token);
            return token;
        } catch (Exception e) {
            logger.warn("登录失败:[{}]", e.getMessage(), e);
            throw new ApplicationException("登录失败:" + e.getMessage());
        }
    }
}
