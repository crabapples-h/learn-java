package cn.crabapples.common.base;

import cn.crabapples.common.dic.DIC;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * TODO 通用返回值DTO
 *
 * @author Mr.He
 * 2019/9/21 17:45
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
@Setter
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private Object data;
    private Long time;
    private String serverName = System.getenv("HOSTNAME");

    public ResponseDTO() {
        this.returnSuccess();
    }

    public ResponseDTO(T data) {
        this.status = ResponseCode.SUCCESS.getCode();
        this.message = DIC.BASE_SUCCESS_MESSAGE;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(String message, T data) {
        this.status = ResponseCode.SUCCESS.getCode();
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(ResponseCode status, String message) {
        this.status = status.getCode();
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    private ResponseDTO(ResponseCode status, String message, T data) {
        this.status = status.getCode();
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }


    public ResponseDTO(int code, String message, T data) {
        this.status = code;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    /*---------------- SUCCESS -------------*/
    public ResponseDTO<T> returnSuccess() {
        this.status = ResponseCode.SUCCESS.getCode();
        this.message = DIC.BASE_SUCCESS_MESSAGE;
        return this;
    }

    public ResponseDTO<T> returnSuccess(String message) {
        this.status = ResponseCode.SUCCESS.getCode();
        this.message = message;
        return this;
    }


    /*---------------- ERROR -----------------*/
    public ResponseDTO<T> returnError() {
        this.status = ResponseCode.ERROR.getCode();
        return this;
    }

    public ResponseDTO<T> returnError(String message) {
        this.status = ResponseCode.ERROR.getCode();
        this.message = message;
        return this;
    }

    /*---------------- AUTH_FAIL -------------*/
    public ResponseDTO<T> returnAuthFail(String message) {
        this.status = ResponseCode.AUTH_FAIL.getCode();
        this.message = message;
        return this;
    }

    public ResponseDTO<T> returnAuthFail(T data, String message) {
        this.status = ResponseCode.AUTH_FAIL.getCode();
        this.message = message;
        return this;
    }

    /*---------------- CUSTOM ----------------*/
    public ResponseDTO<T> returnCustom(int status, String message, T data) {
        this.status = status;
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
