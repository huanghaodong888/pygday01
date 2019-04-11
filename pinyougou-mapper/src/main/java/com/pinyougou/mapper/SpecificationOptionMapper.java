package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.SpecificationOption;

import java.util.List;

/**
 * SpecificationOptionMapper 数据访问接口
 * @date 2018-10-31 15:50:48
 * @version 1.0
 */
public interface SpecificationOptionMapper extends Mapper<SpecificationOption>{


    void save(Specification specification);

    List<SpecificationOption> findById(Long id);

}