package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @PostMapping("/save")
    public boolean save(@RequestBody Goods goods) {
        try {
            //获取商家用户名
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.setSellerId(sellerId);
            goodsService.save(goods);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
