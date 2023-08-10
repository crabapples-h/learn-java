package cn.crabapples.system.service.impl;

import cn.crabapples.system.service.SystemDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TODO 系统服务实现类[字典]
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
@Slf4j
public class SystemDictServiceImpl implements SystemDictService {
    @Value("${isCrypt}")
    private boolean isCrypt;


}
