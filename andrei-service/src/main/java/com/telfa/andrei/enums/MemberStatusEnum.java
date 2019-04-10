package com.telfa.andrei.enums;

/**
 * 用户状态枚举类
 * @since 1.8
 */
public enum MemberStatusEnum {

    NORMAL(0, "正常"),
    DISABLED(1, "停用"),
    FREEZE(2, "冻结");

    private Integer code;
    private String value;

    MemberStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static MemberStatusEnum valueOf(Integer code) {
        for(MemberStatusEnum d : values()) {
            if(d.getCode().equals(code)) {
                return d;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
