package com.telfa.andrei.auth;

import com.telfa.andrei.model.SysUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.logging.Logger;

/**
 * session, 取Spring
 * @since 1.8
 */
public class ContextHelper {

    /**
     * 获取当前登录的用户对象, 返回
     * @return 如果当前环境不存在登录信息, 则返回null
     */
    public static SysUserUserDetails currentSysUserDetails() {
        // 从SpringSecurity中获取登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            Object object = authentication.getPrincipal();
            return (SysUserUserDetails) object;
        }
        return null;
    }

    /**
     * 获取当前登录的用户对象, 返回SysUser
     * @return 如果当前环境不存在登录信息, 则返回null
     */
    public static SysUser currentSysUser() {
        SysUserUserDetails sysUserUserDetails = currentSysUserDetails();
        // 获取用户
        SysUser sysUser = null;
        try {
            Assert.notNull(sysUserUserDetails, "当前登录用户为空");
            sysUser = new SysUser();
            BeanUtils.copyProperties(sysUserUserDetails, sysUser);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return sysUser;
    }

}
