package cn.crabapples.test.service;


import cn.crabapples.test.vo.WechatConfig;

public interface WechatTestService {
    String getToken();

    String getTicket();

    WechatConfig sign(String url);
}
