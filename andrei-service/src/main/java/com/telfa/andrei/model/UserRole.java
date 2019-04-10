package com.telfa.andrei.model;


import com.telfa.andrei.base.BaseModel;

import javax.persistence.*;

@Table(name = "`t_user_role`")
public class UserRole extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "`role_id`")
    private Integer roleId;

    /**
     * 用户id
     */
    @Column(name = "`sys_user_id`")
    private Integer sysUserId;

    /**
     * 角色实体
     */
    @Transient
    private Role role;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置用户id
     *
     * @param sysUserId 用户id
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}