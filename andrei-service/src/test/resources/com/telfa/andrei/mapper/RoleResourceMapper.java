package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.RoleResource;
import com.telfa.andrei.model.RoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    long countByExample(RoleResourceExample example);

    int deleteByExample(RoleResourceExample example);

    List<RoleResource> selectByExample(RoleResourceExample example);

    int updateByExampleSelective(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByExample(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);
}