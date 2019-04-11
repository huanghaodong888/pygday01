package com.pinyougou.mapper;

import com.pinyougou.pojo.SpecificationOption;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Specification;

import java.util.List;

/**
 * SpecificationMapper 数据访问接口
 * @date 2018-10-31 15:50:48
 * @version 1.0
 */

public interface SpecificationMapper extends Mapper<Specification>{

    List<Specification> findAll(Specification specification);

    List<SpecificationOption> findSpecById(Long id);

}