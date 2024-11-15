package cn.crabapples.common.base;

import lombok.Getter;

/**
 * TODO http请求返回状态骂
 *
 * @author Mr.He
 * 8/29/20 11:50 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
public enum ResponseCode {
    SUCCESS(200),
    ERROR(500),
    AUTH_FAIL(401);
    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }
}
