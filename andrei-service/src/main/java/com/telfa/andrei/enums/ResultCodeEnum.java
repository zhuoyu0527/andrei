package com.telfa.andrei.enums;

/**
 * 返回的状态码说明
 * @since 1.8
 */
public enum ResultCodeEnum {

    MEMBER_USERNMAE_PASSWORD_ERROR(1001, "账号密码错误"),
    MEMBER_LOGIN_DENED(1002, "该账号已停用，不可登录"),
    MEMBER_AUTHCODE_ERROR(1005, "验证码错误"),
    MEMBER_STATUS_DISABLED(1111, "用户已停用"),
    SINGLE_MEMBER_LOGIN_ERROR(1200, "账号在别处登录");

    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
