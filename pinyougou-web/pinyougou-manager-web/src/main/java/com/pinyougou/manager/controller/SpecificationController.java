package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//规格控制器
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    /**
     * 配置引用服务
     * timeout:连接超时(调用服务方法的时间)
     */
    @Reference(timeout = 10000)
    private SpecificationService specificationService;
    @GetMapping("/findByPage")
    public PageResult findByPage(Specification specification, Integer page, Integer rows) {
        /** GET请求中文转码 */
        if (specification != null && StringUtils.isNoneBlank(specification.getSpecName())) {
            try {
                specification.setSpecName(new String(specification.getSpecName()
                        .getBytes("ISO8859-1"), "UTF-8"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        PageResult byPage = specificationService.findByPage(specification, page, rows);
        return byPage;
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody Specification specification) {
        try {
            specificationService.save(specification);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findSpecById")
    public List<SpecificationOption> findSpecById(Long id) {
        return specificationService.findSepcById(id);
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @PostMapping("/deleteAll")
    public Boolean deleteAll(@RequestBody Long[] ids) {
        try {
            specificationService.deleteAll(ids);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
