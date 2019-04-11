package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.TypeTemplate;

import javax.naming.ldap.PagedResultsControl;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * TypeTemplateMapper 数据访问接口
 * @date 2018-10-31 15:50:48
 * @version 1.0
 */

public interface TypeTemplateMapper extends Mapper<TypeTemplate>{

    List<TypeTemplate> findAll(TypeTemplate typeTemplate);

    @Select("select id,name as text from tb_brand order by id asc ")
    List<Map<String, Object>> findBrandList();
    @Select("select id ,spec_name as text from tb_specification order by id asc")
    List<Map<String, Object>> findSpecList();

    void deleteAll(@Param("ids") Serializable[] ids);

}