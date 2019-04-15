package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceName = "com.pinyougou.service.SpecificationService")
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * 添加方法
     *
     * @param specification
     */
    @Override
    public void save(Specification specification) {
        /*try {
            specificationMapper.insertSelective(specification);
            specificationOptionMapper.save(specification);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }*/
        try {
            specificationMapper.insertSelective(specification);
            for (SpecificationOption specificationOption : specification.getSpecificationOptions()) {
                //将主表中的id转化成从表中的spec_id
                specificationOption.setSpecId(specification.getId());
                specificationOptionMapper.insertSelective(specificationOption);
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    /**
     * 修改方法
     *
     * @param specification
     */
    @Override
    public void update(Specification specification) {
        try {
            //修改specification表里面的规格名称
            specificationMapper.updateByPrimaryKey(specification);
            //修改specificationOption表里面的规格选项和排序
            //先删掉原来的数据
            SpecificationOption so = new SpecificationOption();
            so.setSpecId(specification.getId());
            specificationOptionMapper.delete(so);
            //再保存修改后的数据
            specificationOptionMapper.save(specification);
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }

    /**
     * 根据主键id删除
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {

    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteAll(Serializable[] ids) {
        try {
            for (Serializable id : ids) {
                specificationMapper.deleteByPrimaryKey(id);
                SpecificationOption so = new SpecificationOption();
                so.setSpecId((Long) id);
                specificationOptionMapper.delete(so);
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }


    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public Specification findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Specification> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param specification
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(Specification specification, int page, int rows) {
        System.out.println(specification.getSpecName());
        try {
            PageInfo<Specification> pageInfo = PageHelper.startPage(page,rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    specificationMapper.findAll(specification);
                }
            });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * 根据id查询规格选项表
     *
     * @param id
     */
    @Override
    public List<SpecificationOption> findSepcById(Long id) {
        try {
           /* SpecificationOption so = new SpecificationOption();
            so.setSpecId(id);
            return specificationOptionMapper.select(so);*/
           return specificationOptionMapper.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Map<String, Object>> findSpecList() {
        return specificationOptionMapper.findSpeclist();
    }
}
