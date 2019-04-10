package com.telfa.andrei.service;

import com.telfa.andrei.mapper.RoleResourceMapper;
import com.telfa.andrei.model.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 角色与资源关联业务逻辑处理类
 */
@Service
public class RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 获取所有有效的角色和资源列表
     */
    public List<RoleResource> listAllRoleResource() {
        return roleResourceMapper.listAllRoleResource();
    }

}
