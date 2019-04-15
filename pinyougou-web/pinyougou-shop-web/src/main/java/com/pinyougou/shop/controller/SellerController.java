package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference(timeout = 10000)
    private SellerService sellerService;
    //商家申请入驻
    @PostMapping("/save")
    public boolean save(@RequestBody Seller seller) {
         try {
             //密码加密
             BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
             String password = passwordEncoder.encode(seller.getPassword());
             seller.setPassword(password);
             sellerService.save(seller);
             return true;
         } catch(Exception ex) {
             ex.printStackTrace();
         }
         return false;
    }
}
