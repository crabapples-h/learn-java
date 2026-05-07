package cn.crabapples.test.service.impl;


import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.test.service.WechatTestService;
import cn.crabapples.test.vo.TokenResult;
import cn.crabapples.test.vo.WechatConfig;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WechatTestServiceImpl implements WechatTestService {
    // test
    private static final String APPID = "wx0f6e9947ab89a6e3";
    private static final String APP_SECRET = "34893470392a81e9a5ba9ad09037943f";
    // mine
//    private static final String APPID = "wx5e4bd217fdb0c9d4";
//    private static final String APP_SECRET = "fbe64bffcdadd875ac3b23bec847eacc";
    // cmcc
//    private static final String APPID = "wx47f50ffe0b7b0839";
//    private static final String APP_SECRET = "3536feffc84c5599b7de4f4fa2dccfef";

    private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
    private static final String TOKEN_URL = "{0}/token?grant_type=client_credential&appid={1}&secret={2}";
    private static final String TICKET_URL = "{0}/ticket/getticket?access_token={1}&type=jsapi";

    private static final String REDIS_TOKEN_KEY = "WECHAT:TOKEN:{0}";
    private static final String REDIS_TICKET_KEY = "WECHAT:TICKET:{0}";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public WechatTestServiceImpl(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getToken() {
        String key = MessageFormat.format(REDIS_TOKEN_KEY, APPID);
        Boolean hasKey = redisTemplate.hasKey(key);
        log.info("redis中是否存在key:[{}],token:[{}]", key, hasKey);
        if (Boolean.TRUE.equals(hasKey)) {
            String token = redisTemplate.opsForValue().get(key);
            log.info("redis中的key:[{}],token:[{}]", key, token);
            return token;
        }
        String url = MessageFormat.format(TOKEN_URL, BASE_URL, APPID, APP_SECRET);
        TokenResult result = restTemplate.getForObject(url, TokenResult.class);
        log.info("获取token 返回数据:[{}]", result);
        if (Objects.isNull(result) || StringUtils.isEmpty(result.getAccess_token())) {
            log.error("token 获取失败:[{}]", result);
            throw new ApplicationException("token 获取失败");
        }
        String token = result.getAccess_token();
        redisTemplate.opsForValue().set(key, token, 60, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public String getTicket() {
        String key = MessageFormat.format(REDIS_TICKET_KEY, APPID);
        Boolean hasKey = redisTemplate.hasKey(key);
        log.info("redis中是否存在key:[{}],token:[{}]", key, hasKey);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String ticket = redisTemplate.opsForValue().get(key);
            log.info("redis中的key:[{}],ticket:[{}]", key, ticket);
            return ticket;
        }
        String token = getToken();
        String url = MessageFormat.format(TICKET_URL, BASE_URL, token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = response.getBody();
            if (Objects.nonNull(responseBody)) {
                String ticket = responseBody.getString("ticket");
                redisTemplate.opsForValue().set(key, ticket, 60, TimeUnit.MINUTES);
                return ticket;
            }
        }
        log.error("ticket 获取失败:[{}]", response);
        throw new RuntimeException("ticket 获取失败");
    }

    @Override
    public WechatConfig sign(String url) {
        String nonceStr = RandomUtil.randomString(16);
        String ticket = getTicket();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signSource = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}",
                ticket, nonceStr, timestamp, URLDecoder.decode(url, Charset.defaultCharset()));
        String sign = DigestUtils.sha1Hex(signSource);
        WechatConfig config = WechatConfig.builder()
                .debug(true)
                .appId(APPID)
                .timestamp(timestamp)
                .nonceStr(nonceStr)
                .signature(sign)
                .build();
        System.err.println(config);
        return config;
    }
}
