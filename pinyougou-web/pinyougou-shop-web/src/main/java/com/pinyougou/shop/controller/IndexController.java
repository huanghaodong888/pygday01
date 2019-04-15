package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.SellerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    @GetMapping("/showUsername")
    public Map<String,String> showUsername() {
        try {
            //获取用户名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Map<String, String> data = new HashMap<>();
            data.put("username",username);
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
