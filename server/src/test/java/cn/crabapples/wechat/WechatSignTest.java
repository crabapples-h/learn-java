package cn.crabapples.wechat;

import cn.crabapples.test.vo.TokenResult;
import cn.crabapples.test.vo.WechatConfig;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class WechatSignTest {
    private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
    private static final String REDIS_KEY = "WECHAT:{0}:{1}";
    private static final String REDIS_TOKEN_KEY = "WECHAT:TOKEN:wx0f6e9947ab89a6e3";
    private static final String REDIS_TICKET_KEY = "WECHAT:TICKET:wx0f6e9947ab89a6e3";
    private static final String APPID = "wx0f6e9947ab89a6e3";
    private static final String APP_SECRET = "34893470392a81e9a5ba9ad09037943f";
    private static final String TOKEN_URL = "{0}/token?grant_type=client_credential&appid={1}&secret={2}";
    private static final String TICKET_URL = "{0}/ticket/getticket?access_token={1}&type=jsapi";

    @Value("${wechatDebug:false}")
    private boolean isDebug;// 开发时测试使用

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 获取微信token
     */
    private String getToken() {
        Boolean hasKey = redisTemplate.hasKey(REDIS_TOKEN_KEY);
        log.info("redis中是否存在key:[{}],token:[{}]", REDIS_TOKEN_KEY, hasKey);
        if (Boolean.TRUE.equals(hasKey)) {
            String token = redisTemplate.opsForValue().get(REDIS_TOKEN_KEY);
            log.info("redis中的key:[{}],token:[{}]", REDIS_TOKEN_KEY, token);
            return token;
        }
        String url = MessageFormat.format(TOKEN_URL, BASE_URL, APPID, APP_SECRET);
        TokenResult result = restTemplate.getForObject(url, TokenResult.class);
        log.info("获取token 返回数据:[{}]", result);
        if (Objects.isNull(result) || StringUtils.isEmpty(result.getAccess_token())) {
            log.error("token 获取失败:[{}]", result);
            throw new RuntimeException("token 获取失败");
        }
        String token = result.getAccess_token();
        redisTemplate.opsForValue().set(REDIS_TOKEN_KEY, token, 60, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 获取微信ticket
     */
    public String getTicket() {
        Boolean hasKey = redisTemplate.hasKey(REDIS_TICKET_KEY);
        log.info("redis中是否存在key:[{}],token:[{}]", REDIS_TICKET_KEY, hasKey);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_TICKET_KEY))) {
            String ticket = redisTemplate.opsForValue().get(REDIS_TICKET_KEY);
            log.info("redis中的key:[{}],ticket:[{}]", REDIS_TICKET_KEY, ticket);
            return ticket;
        }
        String token = getToken();
        String url = MessageFormat.format(TICKET_URL, BASE_URL, token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = response.getBody();
            if (Objects.nonNull(responseBody)) {
                String ticket = responseBody.getString("ticket");
                redisTemplate.opsForValue().set(REDIS_TICKET_KEY, ticket, 60, TimeUnit.MINUTES);
                log.info("获取ticket 返回数据:[{}]", ticket);
                return ticket;
            }
        }
        log.error("ticket 获取失败:[{}]", response);
        throw new RuntimeException("ticket 获取失败");
    }

    @Test
    public void sign() {
        String url = "";
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
                .nonceStr(nonceStr).
                signature(sign).build();
        System.err.println(config);
    }

//    public static void main(String[] args) {
//        String url ="";
//        String nonceStr = RandomUtil.randomString(16);
//        String ticket = getTicket();
//        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//        String signSource = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}",
//                ticket, nonceStr, timestamp, URLDecoder.decode(url, Charset.defaultCharset()));
//        String sign = DigestUtils.sha1Hex(signSource);
//        WechatConfig config = WechatConfig.builder()
//                .debug(true)
//                .appId(APPID)
//                .timestamp(timestamp)
//                .nonceStr(nonceStr).
//                signature(sign).build();
//        System.err.println(config);
//    }
}
