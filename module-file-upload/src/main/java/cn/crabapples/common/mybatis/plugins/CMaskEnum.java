package cn.crabapples.common.mybatis.plugins;

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
        return source.replaceAll(this.regex, this.maskFormat);
    }

    public static void main(String[] args) {
//        System.out.println(CMaskEnum.PHONE.mask("13765710705"));
//        System.out.println(CMaskEnum.ID_CARD.mask("520201199701214811"));
//        System.out.println(CMaskEnum.EMAIL.mask("294046317@qq.com"));
//        System.out.println(CMaskEnum.BANK_CARD.mask("6216612800005972582"));
    }
}
