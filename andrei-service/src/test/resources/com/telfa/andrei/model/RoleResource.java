package com.telfa.andrei.model;

import javax.persistence.*;

@Table(name = "`t_role_resource`")
public class RoleResource {
    @Id
    @Column(name = "`RESOURCE_ID`")
    private Integer resourceId;

    @Column(name = "`ROLE_ID`")
    private Integer roleId;

    /**
     * @return RESOURCE_ID
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return ROLE_ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}