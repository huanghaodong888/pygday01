package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
@Service(interfaceName="com.pinyougou.service.ItemCatService")
@Transactional
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;
    /**
     * 添加方法
     *
     * @param itemCat
     */
    @Override
    public void save(ItemCat itemCat) {

    }

    /**
     * 修改方法
     *
     * @param itemCat
     */
    @Override
    public void update(ItemCat itemCat) {

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

    }

    /**
     * 根据主键id查询
     *
     * @param id
     */
    @Override
    public ItemCat findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<ItemCat> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param itemCat
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(ItemCat itemCat, int page, int rows) {
        //分页查询
        PageInfo<ItemCat> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                itemCatMapper.selectAll();
            }
        });
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }


    @Override
    public List<ItemCat> findByParentId(Long id) {
        try {
            return itemCatMapper.findByParentId(id);
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }
}
