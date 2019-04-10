package com.telfa.andrei.model;

import javax.persistence.*;

@Table(name = "`t_role`")
public class Role {
    /**
     * 主键
     */
    @Id
    @Column(name = "`role_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    /**
     * 角色名称
     */
    @Column(name = "`role_name`")
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "`role_code`")
    private String roleCode;

    /**
     * 角色描述
     */
    @Column(name = "`role_desc`")
    private String roleDesc;

    /**
     * 建立人id
     */
    @Column(name = "`creator`")
    private Integer creator;

    /**
     * 建立日期
     */
    @Column(name = "`create_time`")
    private Integer createTime;

    /**
     * 更新人id
     */
    @Column(name = "`updator`")
    private Integer updator;

    /**
     * 更新日期
     */
    @Column(name = "`update_time`")
    private Integer updateTime;

    /**
     * 是否有效,0:有效, 1:无效
     */
    @Column(name = "`disabled`")
    private Integer disabled;

    /**
     * 状态, 0:正常, 1:停用
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 获取主键
     *
     * @return role_id - 主键
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置主键
     *
     * @param roleId 主键
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取角色编码
     *
     * @return role_code - 角色编码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色编码
     *
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * 获取角色描述
     *
     * @return role_desc - 角色描述
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * 设置角色描述
     *
     * @param roleDesc 角色描述
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    /**
     * 获取建立人id
     *
     * @return creator - 建立人id
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 设置建立人id
     *
     * @param creator 建立人id
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 获取建立日期
     *
     * @return create_time - 建立日期
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置建立日期
     *
     * @param createTime 建立日期
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人id
     *
     * @return updator - 更新人id
     */
    public Integer getUpdator() {
        return updator;
    }

    /**
     * 设置更新人id
     *
     * @param updator 更新人id
     */
    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    /**
     * 获取更新日期
     *
     * @return update_time - 更新日期
     */
    public Integer getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新日期
     *
     * @param updateTime 更新日期
     */
    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否有效,0:有效, 1:无效
     *
     * @return disabled - 是否有效,0:有效, 1:无效
     */
    public Integer getDisabled() {
        return disabled;
    }

    /**
     * 设置是否有效,0:有效, 1:无效
     *
     * @param disabled 是否有效,0:有效, 1:无效
     */
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    /**
     * 获取状态, 0:正常, 1:停用
     *
     * @return status - 状态, 0:正常, 1:停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态, 0:正常, 1:停用
     *
     * @param status 状态, 0:正常, 1:停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}