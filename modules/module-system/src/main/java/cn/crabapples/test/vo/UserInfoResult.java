package cn.crabapples.test.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class UserInfoResult implements Serializable {
    private Integer errcode;
    private String errmsg;
    private String userid;
    private String name;
    private List<String> department;
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private String avatar;
    private Integer status;
    private Integer isleader;
    private ExtAttr extattr;
    private String english_name;
    private String telephone;
    private Integer enable;
    private Integer hide_mobile;
    private List<String> order;
    private String qr_code;
    private List<String> is_leader_in_dept;
    private List<String> positions;
    private String country_code;
    private ExternalProfile external_profile;

    @Getter
    @Setter
    static class ExtAttr {
        private List<String> attrs;
    }

    @Getter
    @Setter
    static class ExternalProfile {
        private String external_corp_name;

    }
}
