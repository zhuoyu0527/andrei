package com.telfa.andrei.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类
 * @since 1.8
 */
@Component
public class Constants {

    /**
     * 代表逻辑是
     */
    public static final Integer YES = 1;

    /**
     * 代表逻辑否
     */
    public static final Integer NO = 0;

    /**
     * 代表禁用
     */
    public static final Byte DISABLE = 1;

    /**
     * 代表可用
     */
    public static final Byte AVAILABLE = 0;

    /**
     * 代表使用
     */
    public static final Byte USED = 1;

    /**
     * 代表未使用
     */
    public static final Byte NOTUSED = 0;

    /**
     * 代表等待
     */
    public static final Byte LOADING = 2;

    /**
     * 代表处理中
     */
    public static final Byte WORKING= 3;

    /**
     * 代表错误
     */
    public static final Byte WORKING_ERROR= 4;

    /**
     * 代表成功字符串
     */
    public static final String SUCCESS = "success";

    /**
     * 代表失败字符串
     */
    public static final String FAILURE = "failure";

    /**
     * 代表错误字符串
     */
    public static final String ERROR = "error";

    public static final String REDIS_KEY = "andrei:";
}
