package demo.mybatis;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Idcard {
    private String zip;
    private String address;
    private String ctfId;
    private String ctfTp;
    private String birthday;
    private String gender;
    private Integer id;
    private String email;
    private String mobile;
    private String name;
    private String version;
    private String nation;
    private String company;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
