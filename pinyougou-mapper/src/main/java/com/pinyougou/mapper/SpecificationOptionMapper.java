package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.SpecificationOption;

import java.util.List;
import java.util.Map;

/**
 * SpecificationOptionMapper 数据访问接口
 * @date 2018-10-31 15:50:48
 * @version 1.0
 */
public interface SpecificationOptionMapper extends Mapper<SpecificationOption>{


    void save(Specification specification);

    List<SpecificationOption> findById(Long id);


    @Select("select id ,spec_name as text from tb_specification order by id asc")
    List<Map<String, Object>> findSpeclist();
}