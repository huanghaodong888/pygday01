package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    @GetMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        try {
            return itemCatService.findByParentId(parentId);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
