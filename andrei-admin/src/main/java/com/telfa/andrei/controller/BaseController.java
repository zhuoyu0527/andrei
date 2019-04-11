package com.telfa.andrei.controller;

import com.telfa.andrei.auth.ContextHelper;
import com.telfa.andrei.base.WebResult;
import com.telfa.andrei.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 处理系统基本请求
 * @since 1.8
 */
@Controller
@RequestMapping("/")
public class BaseController {

    private final SysUserService sysUserService;


    @Autowired
    public BaseController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping("")
    public String index() {
        if(ContextHelper.currentSysUserDetails() == null) {
            return "login";
        }
        return "index";
    }

    /**
     * 到登录界面去
     * @return 登录界面地址
     */
    @GetMapping("login")
    public String toLoginPage() {
        return "login";
    }

    /**
     * 获取所有session
     * @param httpSession session对象
     * @return session的key和value列表
     */
    @GetMapping("sessions")
    @ResponseBody
    public WebResult listAllSession(HttpSession httpSession) {
        Enumeration<String> sessions = httpSession.getAttributeNames();
        List<Pair<String, String>> sessionPairs = new ArrayList<>();
        while(sessions.hasMoreElements()) {
            String key = sessions.nextElement();
            sessionPairs.add(Pair.of(key, httpSession.getAttribute(key).toString()));
        }
        return new WebResult().success().data(sessionPairs);
    }

    /**
     * 系统用户登出处理
     * @return 重定向到登录页
     */
    @GetMapping("logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }

}