package cn.crabapples.test.service.impl;


import cn.crabapples.common.ApplicationException;
import cn.crabapples.test.service.WechatTestService;
import cn.crabapples.test.vo.WechatConfig;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.RandomUtil;
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

@Service
@Slf4j
public class WechatTestServiceImpl implements WechatTestService {
    private static final String APPID = "xxxxx";
    private static final String APP_SECRET = "xxxxx";
    private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
    private static final String TOKEN_URL = BASE_URL + "/token?grant_type=client_credential&appid={0}&secret={1}";
    private static final String TICKET_URL = BASE_URL + "/ticket/getticket?access_token={0}&type=jsapi";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public WechatTestServiceImpl(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getToken() {
        String token = redisTemplate.opsForValue().get("wechat:token");
        if (StringUtils.isEmpty(token)) {
            String url = MessageFormat.format(TOKEN_URL, APPID, APP_SECRET);
            ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseBody = response.getBody();
                if (Objects.nonNull(responseBody)) {
                    token = responseBody.getString("access_token");
                    redisTemplate.opsForValue().set("wechat:token", token);
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
        String ticket = redisTemplate.opsForValue().get("wechat:ticket");
        if (StringUtils.isEmpty(ticket)) {
            String token = getToken();
            String url = MessageFormat.format(TICKET_URL, token);
            ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseBody = response.getBody();
                if (Objects.nonNull(responseBody)) {
                    ticket = responseBody.getString("ticket");
                    redisTemplate.opsForValue().set("wechat:ticket", ticket);
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
        String nonceStr = RandomUtil.randomString(16);
        String ticket = getTicket();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signSource = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}",
                ticket, nonceStr, timestamp, URLEncoder.DEFAULT.encode(url, Charset.defaultCharset()));
        String sign = DigestUtils.sha1Hex(signSource);
        WechatConfig config = new WechatConfig();
        config.setAppId(APPID);
        config.setTimestamp(timestamp);
        config.setNonceStr(nonceStr);
        config.setSign(sign);
        System.err.println(config);
        return config;
    }
}
