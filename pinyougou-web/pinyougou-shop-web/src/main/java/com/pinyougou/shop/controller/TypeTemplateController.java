package com.pinyougou.shop.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Reference(timeout=10000)
    private TypeTemplateService typeTemplateService;
    @GetMapping("/findOne")
    //根据模板ID查询brand_ids
    public TypeTemplate findOne(Long id) {
        try {
           return typeTemplateService.findOne(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //根据模板ID查询spec_ids
    @GetMapping("/findSpecIds")
    public List<Map> findSpecIds(Long id) {
        try {
            return typeTemplateService.findSpecIds(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
