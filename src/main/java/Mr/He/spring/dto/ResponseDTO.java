package Mr.He.spring.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 * TODO 通用返回值DTO
 *
 * @author Mr.He
 * @date 2019/9/21 17:45
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
public class ResponseDTO{

    private static final int SUCCESS = 200;
    private static final int ERROR = 500;
    private int status;
    private String message;
    private Object data;
    private long time;

    private ResponseDTO(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }
    public static ResponseDTO returnSuccess(String message, Object data){
        return new ResponseDTO(SUCCESS,message,data);
    }
    public static ResponseDTO returnSuccess(String message){
        return new ResponseDTO(SUCCESS,message,null);
    }
    public static ResponseDTO returnError(String message){
        return new ResponseDTO(ERROR,message,null);
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
