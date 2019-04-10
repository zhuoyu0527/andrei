package com.telfa.andrei.model;

import javax.persistence.*;

@Table(name = "`t_sys_user`")
public class SysUser {
    /**
     * 主键
     */
    @Id
    @Column(name = "`sys_user_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sysUserId;

    /**
     * 登录名
     */
    @Column(name = "`username`")
    private String username;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "`realname`")
    private String realname;

    /**
     * 部门
     */
    @Column(name = "`department`")
    private String department;

    /**
     * 职位
     */
    @Column(name = "`position`")
    private String position;

    /**
     * 状态, 0正常, 1停用
     */
    @Column(name = "`status`")
    private Byte status;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 建立人
     */
    @Column(name = "`creator`")
    private Integer creator;

    /**
     * 建立时间
     */
    @Column(name = "`create_time`")
    private Integer createTime;

    /**
     * 更新人
     */
    @Column(name = "`updator`")
    private Integer updator;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Integer updateTime;

    /**
     * 获取主键
     *
     * @return sys_user_id - 主键
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置主键
     *
     * @param sysUserId 主键
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取登录名
     *
     * @return username - 登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录名
     *
     * @param username 登录名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * 获取部门
     *
     * @return department - 部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置部门
     *
     * @param department 部门
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 获取职位
     *
     * @return position - 职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置职位
     *
     * @param position 职位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取状态, 0正常, 1停用
     *
     * @return status - 状态, 0正常, 1停用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态, 0正常, 1停用
     *
     * @param status 状态, 0正常, 1停用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取建立人
     *
     * @return creator - 建立人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 设置建立人
     *
     * @param creator 建立人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 获取建立时间
     *
     * @return create_time - 建立时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置建立时间
     *
     * @param createTime 建立时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return updator - 更新人
     */
    public Integer getUpdator() {
        return updator;
    }

    /**
     * 设置更新人
     *
     * @param updator 更新人
     */
    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Integer getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}