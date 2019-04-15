package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class LoginController {
    @GetMapping("/showLoginName")
   //显示用户名
    public Map<String,String > showLoginName() {
        try {
            //获取登录用户名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //创建一个HashMap
            Map<String, String> data = new HashMap<>();
            data.put("userName",username);
            return data;
        } catch (Exception ex) {
            return null;
        }
    }
}
