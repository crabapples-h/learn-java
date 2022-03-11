package cn.crabapples.system.dto;

import cn.crabapples.common.ResponseCode;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO 返回集合DTO
 *
 * @author Mr.He
 * 8/29/20 11:48 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class ResponseArrayDTO extends PageDTO {
    private int status;
    private String message;
    private Object data;
    private long time;

    private ResponseArrayDTO(ResponseCode status, String message, Object data) {
        this.status = status.getCode();
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    private ResponseArrayDTO(int code, String message, Object data) {
        this.status = code;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public static ResponseArrayDTO returnSuccess(String message, Object data) {
        return new ResponseArrayDTO(ResponseCode.SUCCESS, message, data);
    }

    public static ResponseArrayDTO returnSuccess(String message) {
        return returnSuccess(message, null);

    }

    public static ResponseArrayDTO returnError(String message, Object data) {
        return new ResponseArrayDTO(ResponseCode.ERROR, message, data);
    }

    public static ResponseArrayDTO returnError(String message) {
        return returnError(message, null);
    }

    public static ResponseArrayDTO returnAuthFail(String message, Object data) {
        return new ResponseArrayDTO(ResponseCode.AUTH_FAIL, message, data);
    }

    public static ResponseArrayDTO returnAuthFail(String message) {
        return returnAuthFail(message, null);
    }

    public static ResponseArrayDTO returnCustom(int status, String message, Object data) {
        return new ResponseArrayDTO(status, message, data);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
