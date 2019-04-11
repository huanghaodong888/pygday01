package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 品牌数据访问接口
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2018-10-29<p>
 */
public interface BrandMapper extends Mapper<Brand> {
    List<Brand> findAll(Brand brand);
    void deleteAll(Serializable[] ids);
}
