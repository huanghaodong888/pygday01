package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

//商家审核控制器
@RestController
@RequestMapping("/seller")
public class SellerCkeckController {
    @Reference(timeout = 10000)
    private SellerService sellerService;
    @GetMapping("/findByPage")
    public PageResult findByName(Seller seller,Integer page,Integer rows) {
        //GET请求中文转码
        try {
            if (seller != null && StringUtils.isNoneBlank(seller.getName())) {
                seller.setName(new String(seller.getName().getBytes("ISO8859-1"),"UTF-8"));
            }
            if (seller != null && StringUtils.isNoneBlank(seller.getNickName())) {
                seller.setNickName(new String(seller.getNickName().getBytes("ISO8859-1"),"UTF-8"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       return  sellerService.findByPage(seller, page, rows);
    }

    @GetMapping("/updateStatus")
    public boolean updateStatus( String sellerId, String status) {
        try {
            sellerService.updateStatus(sellerId,status);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
