package cn.crabapples.common.mybatis.plugins.mask;

import lombok.Getter;

@Getter
public enum CMaskEnum {
    PHONE("手机号", "^(1[3-9])\\d+(\\d{3})$", "$1*****$2"),
    ID_CARD("身份证", "^(\\d{2})\\d*(\\d|[xX])$", "$1*************$2"),
    EMAIL("邮箱", "^(\\w)(\\w)*(@)(\\w*\\.\\w*)$", "$1****$2$3$4"),
    BANK_CARD("银行卡", "^(\\d{2})\\d*(\\d{3})$", "$1*************$2");
    private final String name;
    private final String regex;
    private final String maskFormat;

    CMaskEnum(String name, String regex, String maskFormat) {
        this.name = name;
        this.regex = regex;
        this.maskFormat = maskFormat;
    }

    public String mask(String source) {
        if (source == null) {
            return null;
        }
        return source.replaceAll(this.regex, this.maskFormat);
    }
}
