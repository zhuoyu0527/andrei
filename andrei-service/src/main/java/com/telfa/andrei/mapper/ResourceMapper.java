package com.telfa.andrei.mapper;


import com.telfa.andrei.base.BaseMapper;
import com.telfa.andrei.model.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据用户id获取用户资源权限集合
     * @param sysUserId 用户id
     *
     */
    List<Resource> listResourceBySysUserId(Integer sysUserId);
}