package demo.mybatis;

import com.alibaba.fastjson.JSONObject;

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

    public String getZip() {
        return this.zip;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCtfId() {
        return this.ctfId;
    }

    public String getCtfTp() {
        return this.ctfTp;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public Integer getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getNation() {
        return this.nation;
    }

    public String getCompany() {
        return this.company;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCtfId(String ctfId) {
        this.ctfId = ctfId;
    }

    public void setCtfTp(String ctfTp) {
        this.ctfTp = ctfTp;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
