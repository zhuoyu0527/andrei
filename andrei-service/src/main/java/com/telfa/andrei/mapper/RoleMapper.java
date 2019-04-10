package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.Role;

import java.util.List;

/**
 *
 * 角色数据操作接口
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取用户所属角色
     * @param sysUserId 用户id
     *
     */
    List<Role> listRoleBySysUserId(Integer sysUserId);
}