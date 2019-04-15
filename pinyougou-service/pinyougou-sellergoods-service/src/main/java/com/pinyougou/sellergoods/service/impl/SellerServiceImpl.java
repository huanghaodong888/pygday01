package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Service(interfaceName="com.pinyougou.service.SellerService")
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerMapper sellerMapper;
    /**
     * 添加方法
     *
     * @param seller
     */
    @Override
    //点击申请
    public void save(Seller seller) {
        try {
            seller.setStatus("0");
            seller.setCreateTime(new Date());
            sellerMapper.insertSelective(seller);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    /**
     * 修改方法
     *
     * @param seller
     */
    @Override
    public void update(Seller seller) {

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
    public Seller findOne(Serializable id) {
        Seller seller = new Seller();
        seller.setSellerId((String) id);
        return sellerMapper.selectOne(seller);
    }

    /**
     * 查询全部
     */
    @Override
    public List<Seller> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param seller
     * @param page
     * @param rows
     */
    @Override
    public PageResult findByPage(Seller seller, int page, int rows) {
        try {
            PageInfo<Seller> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sellerMapper.findAll(seller);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        try {
            sellerMapper.updateStatus(sellerId,status);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
