package cn.crabapples.test.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TokenResult implements Serializable {
    private Integer errcode;
    private String errmsg;
    private String access_token;
    private Integer expires_in;

}
