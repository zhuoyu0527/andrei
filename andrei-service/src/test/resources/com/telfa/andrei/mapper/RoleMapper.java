package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.Role;
import com.telfa.andrei.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role> {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    List<Role> selectByExample(RoleExample example);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);
}