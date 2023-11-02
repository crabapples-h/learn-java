package cn.crabapples.test.service.impl;


import cn.crabapples.common.ApplicationException;
import cn.crabapples.test.service.WechatTestService;
import cn.crabapples.test.vo.WechatConfig;
import cn.hutool.core.net.URLEncoder;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
//    private static final String APP_SECRET = "c24295993b4947c92263ecf47c35276a";
    // cmcc
//    private static final String APPID = "wx47f50ffe0b7b0839";
//    private static final String APP_SECRET = "3536feffc84c5599b7de4f4fa2dccfef";

    private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
    private static final String TOKEN_URL = "{0}/token?grant_type=client_credential&appid={1}&secret={2}";

    private static final String TICKET_URL = "{0}/ticket/getticket?access_token={1}&type=jsapi";
    private static final String TOKEN_KEY = "WECHAT:TOKEN:{0}";
    private static final String TICKET_KEY = "WECHAT:TICKET:{0}";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    public WechatTestServiceImpl(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getToken() {
        String redisKey = MessageFormat.format(TOKEN_KEY, APPID);
        String token = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(token)) {
            String url = MessageFormat.format(TOKEN_URL, BASE_URL, APPID, APP_SECRET);
            ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseBody = response.getBody();
                if (Objects.nonNull(responseBody)) {
                    token = responseBody.getString("access_token");
                    redisTemplate.opsForValue().set(redisKey, token, 90, TimeUnit.MINUTES);
                    return token;
                }
            }
            log.error("token 获取失败:[{}]", response);
            throw new ApplicationException("token 获取失败");
        }
        return token;
    }

    @Override
    public String getTicket() {
        String redisKey = MessageFormat.format(TICKET_KEY, APPID);
        String ticket = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(ticket)) {
            String token = getToken();
            String url = MessageFormat.format(TICKET_URL, BASE_URL, token);
            ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseBody = response.getBody();
                if (Objects.nonNull(responseBody)) {
                    ticket = responseBody.getString("ticket");
                    redisTemplate.opsForValue().set(redisKey, ticket, 90, TimeUnit.MINUTES);
                    return ticket;
                }
            }
            log.error("ticket 获取失败:[{}]", response);
            throw new ApplicationException("ticket 获取失败");
        }
        return ticket;
    }

    @Override
    public WechatConfig sign(String url) {
//        String nonceStr = RandomUtil.randomString(16);
        String nonceStr = "aaaaaaaa";
        String ticket = getTicket();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signSource = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}",
                ticket, nonceStr, timestamp, URLEncoder.DEFAULT.encode(url, Charset.defaultCharset()));
        String sign = DigestUtils.sha1Hex(signSource);
        WechatConfig config = WechatConfig.builder().appId(APPID).timestamp(timestamp).nonceStr(nonceStr).sign(sign).build();
        System.err.println(signSource);
        System.err.println(config);
        return config;
    }
}
