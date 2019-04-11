package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class itemCatController {
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;
   /* //分页查询全部数据
    @GetMapping("/findAll")
    public PageResult findByPage(Integer page,Integer rows) {
        try {
            return itemCatService.findByPage(null,page,rows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }*/
   //根据父级id查询
    @GetMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        try {
           return itemCatService.findByParentId(parentId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
