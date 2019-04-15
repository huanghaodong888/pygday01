package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceName="com.pinyougou.service.TypeTemplateService")
@Transactional
public class TypeTempalteServiceImpl implements TypeTemplateService {
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * 添加方法
     *
     * @param typeTemplate
     */
    @Override
    public void save(TypeTemplate typeTemplate) {
        try {
            typeTemplateMapper.insertSelective(typeTemplate);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    /**
     * 修改方法
     *
     * @param typeTemplate
     */
    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
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
        typeTemplateMapper.deleteAll(ids);

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public TypeTemplate findOne(Serializable id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     */
    @Override
    public List<TypeTemplate> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param typeTemplate
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(TypeTemplate typeTemplate, int page, int rows) {
        try {
            PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    typeTemplateMapper.findAll(typeTemplate);
                }
            });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Map> findSpecIds(Long id) {
        TypeTemplate typeTemplate = findOne(id);
        String specIds = typeTemplate.getSpecIds();
        //将spedIds转化为List集合[{},{}]
        //将String转化为{}，则JSON.parse
        List<Map> specIdsList = JSON.parseArray(specIds,Map.class);
        for (Map map : specIdsList) {
            String specId = map.get("id").toString();
            SpecificationOption specificationOption = new SpecificationOption();
            specificationOption.setSpecId(Long.valueOf(specId));
            List<SpecificationOption> options = specificationOptionMapper.select(specificationOption);
            map.put("options",options);
        }
        return specIdsList;
    }


}
