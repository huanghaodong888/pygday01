package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    /**
     * 配置引用服务
     * timeout:连接超时(调用服务方法的时间)
     */
    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    @GetMapping("/findAll")
    public PageResult findByPage( TypeTemplate typeTemplate,Integer page, Integer rows) {
        /** GET请求中文转码 */
        if (typeTemplate != null && StringUtils.isNoneBlank(typeTemplate.getName())) {
            try {
                typeTemplate.setName(new String(typeTemplate.getName()
                        .getBytes("ISO8859-1"), "UTF-8"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return typeTemplateService.findByPage(typeTemplate, page, rows);
    }

    @GetMapping("/findBrandList")
    public List<Map<String,Object>> findBrandList() {
        return typeTemplateService.findBrandList();
    }

    @GetMapping("/findSpecList")
    public List<Map<String,Object>> findSpecList() {
        try {
            return typeTemplateService.findSpecList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody TypeTemplate typeTemplate) {
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //删除复选框
    @PostMapping("/deleteAll")
    public Boolean deleteAll(@RequestBody Long[] ids) {
        try {
            typeTemplateService.deleteAll(ids);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody TypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
