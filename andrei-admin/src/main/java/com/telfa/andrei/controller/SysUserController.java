package com.telfa.andrei.controller;

import com.telfa.andrei.auth.SysUserUserDetails;
import com.telfa.andrei.model.SysUser;
import com.telfa.andrei.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenStore tokenStore;


    @RequestMapping("/auth")
    @ResponseBody
    public SysUser Auth(@RequestHeader("Authorization") String auth){
        SysUserUserDetails userDetails = (SysUserUserDetails)tokenStore.readAuthentication(auth.split(" ")[1]).getPrincipal();
        return userDetails;
    }
}
