package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 添加方法
     *
     * @param goods
     */
    @Override
    public void save(Goods goods) {
        try {
            // 设置未申核状态
            goods.setAuditStatus("0");
            goodsMapper.insertSelective(goods);
            // 为商品描述对象设置主键id
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());

            if ("1".equals(goods.getIsEnableSpec())) {
            //迭代所有的SKU具体商品集合，往SKU表插入数据
                for (Item item : goods.getItems()) {
                    //数据库需要的title形式 ： OPPO A79 全面屏拍照手机 移动4G 64G
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(goods.getGoodsName());
                    //将{"网络":"联通4G","机身内存":"128G"}转化为JSON对象Map
                    Map map = JSON.parseObject(item.getSpec());
                    for (Object value : map.values()) {
                        stringBuilder.append(" " + value);
                    }
                    item.setTitle(stringBuilder.toString());
                    setItemInfo(item,goods);
                    itemMapper.insertSelective(item);
                }
            } else {
                Item item = new Item();
                item.setTitle(goods.getGoodsName());
                item.setPrice(goods.getPrice());
                item.setNum(999);
                item.setStatus("1");
                item.setIsDefault("1");
                /** 设置规格选项 */
                item.setSpec("{}");
                /** 设置SKU商品其它属性 */
                setItemInfo(item,goods);
                itemMapper.insertSelective(item);
            }


//            goods.getGoodsDesc().setGoodsId(goods.getId());
//            goodsDescMapper.insertSelective(goods.getGoodsDesc());

        } catch (Exception ex) {
            System.out.println("发生异常的原因为 :" + ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
    private void setItemInfo(Item item, Goods goods) {
        //数据库itemImages的形式 [{"color":"金色","url":"http://image.pinyougou.com/jd/wKgMg1qtKEOATL9nAAFti6upbx4132.jpg"},
        // {"color":"深空灰色","url":"http://image.pinyougou.com/jd/wKgMg1qtKHmAFxj7AAFZsBqChgk725.jpg"},
        // {"color":"银色","url":"http://image.pinyougou.com/jd/wKgMg1qtKJyAHQ9sAAFuOBobu-A759.jpg"}]
        //将其形式转换为JSON数组对象
        List<Map> maps = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (maps != null && maps.size() > 0) {
            item.setImage((String) maps.get(0).get("url"));
        }

        item.setCategoryid(goods.getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());
        item.setGoodsId(goods.getId());
        item.setSellerId(goods.getSellerId());
        //设置商品分类名称
        item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());
        item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());
        System.out.println(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());
        item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName());
    }

    /**
     * 修改方法
     *
     * @param goods
     */
    @Override
    public void update(Goods goods) {

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
    public Goods findOne(Serializable id) {
        return null;
    }

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return null;
    }

    /**
     * 多条件分页查询
     *
     * @param goods
     * @param page
     * @param rows
     */
    /** 多条件分页查询商品 */
    @Override
    public PageResult findByPage(Goods goods,int page,int rows){
        try{
            /** 开始分页 */
            PageInfo<Map<String,Object>> pageInfo =
                    PageHelper.startPage(page, rows)
                            .doSelectPageInfo(new ISelect() {
                                @Override
                                public void doSelect() {
                                    goodsMapper.findAll(goods);
                                }
                            });
            /** 循环查询到的商品 */
            for (Map<String,Object> map : pageInfo.getList()){
                ItemCat itemCat1 =
                        itemCatMapper.selectByPrimaryKey(map.get("category1Id"));
                map.put("category1Name", itemCat1 != null ? itemCat1.getName() : "");
                ItemCat itemCat2 =
                        itemCatMapper.selectByPrimaryKey(map.get("category2Id"));
                map.put("category2Name", itemCat2 != null ? itemCat2.getName() : "");
                ItemCat itemCat3 =
                        itemCatMapper.selectByPrimaryKey(map.get("category3Id"));
                map.put("category3Name", itemCat3 != null ? itemCat3.getName() : "");
            }
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateStatus(String auditStatus, Long[] ids,String columnName) {
        try {
            goodsMapper.updateStatus(auditStatus,ids,columnName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

}
