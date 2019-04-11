package com.telfa.andrei.base;


import com.telfa.andrei.enums.ResultCodeEnum;

/**
 * Web页面ajax数据返回对象
 * @since 1.8
 */
public class WebResult {

    private int code;
    private String msg;
    private long count;
    private Object data;

    public WebResult() {}

    public WebResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebResult(int code, String msg, long count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public WebResult success() {
        this.setCode(200);
        this.setMsg("操作成功");
        return this;
    }
    
    public WebResult success(String msg) {
        code(200);
        msg(msg);
        return this;
    }

    public WebResult data(Object data) {
        this.data = data;
        return this;
    }

    public WebResult error() {
        code(500);
        msg("操作失败");
        return this;
    }

    public WebResult error(String msg) {
        code(500);
        msg(msg);
        return this;
    }

    public WebResult code(int code) {
        this.setCode(code);
        return this;
    }

    public WebResult msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public WebResult result(boolean success) {
        return success ? success() : error();
    }

    public WebResult result(ResultCodeEnum resultCodeEnum) {
        code(resultCodeEnum.getCode());
        msg(resultCodeEnum.getMsg());
        return this;
    }
}
