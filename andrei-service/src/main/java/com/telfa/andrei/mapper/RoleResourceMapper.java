package com.telfa.andrei.mapper;

import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.RoleResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * 获取所有有效的角色和资源列表
     * @return
     */
    List<RoleResource> listAllRoleResource();

}