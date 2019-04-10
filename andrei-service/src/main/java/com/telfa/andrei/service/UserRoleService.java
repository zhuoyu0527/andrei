package com.telfa.andrei.service;

import com.telfa.andrei.mapper.UserRoleMapper;
import com.telfa.andrei.model.UserRole;
import com.telfa.andrei.model.UserRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户和角色关联类业务逻辑
 * @since 1.8
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 新增用户和角色对应关系
     * @param sysUserId 用户id
     * @param roleId 角色id
     */
    public UserRole insertUerRole(Integer sysUserId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setSysUserId(sysUserId);
        userRole.setRoleId(roleId);
        userRoleMapper.insert(userRole);
        return userRole;
    }

    /**
     * 根据用户id获取用户角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    public List<UserRole> listRolesByUserId(Integer sysUserId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria()
                .andSysUserIdEqualTo(sysUserId);
        List<UserRole> roles = userRoleMapper.listRolesBySysUserId(userRoleExample);

        return roles.size() > 0 ? roles.stream()
                .filter(d -> (d.getRole().getStatus().equals(1) && d.getRole().getDisabled().equals(0)))
                .collect(Collectors.toList()) : new ArrayList<>();

    }

}
