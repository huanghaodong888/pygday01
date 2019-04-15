package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 品牌服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2018-10-29<p>
 */
@Service(interfaceName = "com.pinyougou.service.BrandService")
@Transactional
public class BrandServiceImpl implements BrandService {
    /** 注入数据访问接口代理对象 */
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 添加方法
     *
     * @param brand
     */
    @Override
    public void save(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改方法
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {
       brandMapper.deleteAll(ids);
    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Brand findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Brand> findAll() {
      return brandMapper.selectAll();
    }

    /**
     * 多条件分页查询
     *
     * @param brand
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(Brand brand, int page, int rows) {
        try {
            //开始分页
            PageInfo<Brand> pageInfo = PageHelper.startPage(page,rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    brandMapper.findAll(brand);
                }
            });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Map<String, Object>> findBrandList() {
        return brandMapper.findBrandList();
    }

}
