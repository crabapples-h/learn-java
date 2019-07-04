package Mr.He.spring.entity;

import com.alibaba.fastjson.JSON;


public class WebsocketMessage {

    private String sid;

    private String message;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
