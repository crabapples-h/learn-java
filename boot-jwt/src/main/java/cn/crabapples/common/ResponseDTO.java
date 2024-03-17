package cn.crabapples.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private Object data;
    private Long time;

    private ResponseDTO(ResponseCode status, String message, T data) {
        this.status = status.getCode();
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(ResponseCode status, String message) {
        this.status = status.getCode();
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(String message, T data) {
        this.status = ResponseCode.SUCCESS.getCode();
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

    public ResponseDTO(String message) {
        this.status = ResponseCode.SUCCESS.getCode();
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(T data) {
        this.status = ResponseCode.SUCCESS.getCode();
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    /*---------------- SUCCESS -------------*/
    public ResponseDTO<T> returnSuccess(String message, T data) {
        return new ResponseDTO<>(ResponseCode.SUCCESS, message, data);
    }

    public ResponseDTO<T> returnSuccess(T data) {
        return returnSuccess(DIC.BASE_SUCCESS_MESSAGE, data);
    }

    public ResponseDTO<T> returnSuccess() {
        return returnSuccess(DIC.BASE_SUCCESS_MESSAGE, null);
    }


    /*---------------- ERROR -----------------*/
    public static ResponseDTO returnError(String message) {
        return new ResponseDTO<>(ResponseCode.ERROR, message);
    }

    /*---------------- AUTH_FAIL -------------*/
    public static ResponseDTO returnAuthFail(String message) {
        return new ResponseDTO<>(ResponseCode.AUTH_FAIL, message);
    }

    /*---------------- CUSTOM ----------------*/
    public ResponseDTO<T> returnCustom(int status, String message, T data) {
        return new ResponseDTO<>(status, message, data);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
