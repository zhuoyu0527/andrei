package com.telfa.andrei.service;

import com.github.pagehelper.PageHelper;
import com.telfa.andrei.mapper.ResourceMapper;
import com.telfa.andrei.model.Resource;
import com.telfa.andrei.model.ResourceExample;
import com.telfa.andrei.model.Role;
import com.telfa.andrei.model.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统资源(菜单/按钮等)业务逻辑处理
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 根据用户id获取用户资源权限集合
     * @param sysUserId 用户id
     *
     */
    public List<Resource> listResourceBySysUserId(Integer sysUserId) {
        return resourceMapper.listResourceBySysUserId(sysUserId);
    }

    /**
     * 获取所有资源未删除的列表
     */
    public List<Resource> listAllResource() {
        ResourceExample resourceExample = new ResourceExample();
        resourceExample.createCriteria()
                .andStatusEqualTo(0)
                .andDisabledEqualTo(0);
        resourceExample.setOrderByClause(" parent_id, show_order");
        return resourceMapper.selectByExample(resourceExample);
    }
}
