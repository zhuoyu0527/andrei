package com.telfa.andrei.controller;

import com.telfa.andrei.auth.ContextHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author andrei
 * @date 2019/4/6
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("")
    public String index() {
        if(ContextHelper.currentSysUserDetails() == null) {
            return "login";
        }
        return "index";
    }

    @GetMapping("/main")
    public String main() { return "main"; }

}
