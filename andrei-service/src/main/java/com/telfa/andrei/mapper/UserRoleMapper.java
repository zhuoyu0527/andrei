package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.UserRole;
import com.telfa.andrei.model.UserRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id获取用户所属角色列表
     * @param example 查询条件
     * @return 用户所属的角色列表
     */
    List<UserRole> listRolesBySysUserId(UserRoleExample example);

    List<UserRole> listAvailableRoleByBySysUserId(@Param("sysUserId") int sysUserId);

}