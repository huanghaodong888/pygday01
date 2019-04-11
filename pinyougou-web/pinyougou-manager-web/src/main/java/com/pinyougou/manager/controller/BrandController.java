package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2018-10-29<p>
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    /**
     * 配置引用服务
     * timeout:连接超时(调用服务方法的时间)
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    /** 查询全部品牌 */
    @GetMapping("/findAll")
    public List<Brand> findAll(){
        System.out.println("brandService: " + brandService);
        return brandService.findAll();
    }

    //添加
    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand) {
        try {
            brandService.save(brand);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** 分页查询全部品牌 */
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand,Integer page, Integer rows){
        /** GET请求中文转码 */
        if (brand != null && StringUtils.isNoneBlank(brand.getName())) {
            try {
                brand.setName(new String(brand.getName()
                        .getBytes("ISO8859-1"), "UTF-8"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return brandService.findByPage(brand,page,rows);
    }
    //批量删除品牌
    @GetMapping("/delete")
    public Boolean delete(Long[] ids) {
        try {
            brandService.deleteAll(ids);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}