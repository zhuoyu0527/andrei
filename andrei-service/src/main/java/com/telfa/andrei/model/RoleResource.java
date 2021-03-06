package com.telfa.andrei.model;

import javax.persistence.*;

@Table(name = "`t_role_resource`")
public class RoleResource {
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
     * 资源id
     */
    @Column(name = "`resource_id`")
    private Integer resourceId;

    /**
     * 角色编码
     */
    @Transient
    private String roleCode;

    /**
     * 资源url
     */
    @Transient
    private String url;

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
     * 获取资源id
     *
     * @return resource_id - 资源id
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * 设置资源id
     *
     * @param resourceId 资源id
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}