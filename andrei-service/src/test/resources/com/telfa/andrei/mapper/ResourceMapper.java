package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.Resource;
import com.telfa.andrei.model.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper extends BaseMapper<Resource> {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    List<Resource> selectByExample(ResourceExample example);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);
}