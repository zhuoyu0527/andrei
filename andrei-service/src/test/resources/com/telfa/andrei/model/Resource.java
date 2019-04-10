package com.telfa.andrei.model;

import javax.persistence.*;

@Table(name = "`t_resource`")
public class Resource {
    /**
     * 主键
     */
    @Id
    @Column(name = "`resource_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;

    /**
     * 资源名称
     */
    @Column(name = "`resource_name`")
    private String resourceName;

    /**
     * 资源编码
     */
    @Column(name = "`resource_code`")
    private String resourceCode;

    /**
     * 资源访问路径
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 方法名称
     */
    @Column(name = "`method_name`")
    private String methodName;

    /**
     * 图标
     */
    @Column(name = "`icon`")
    private String icon;

    /**
     * 资源类型, 1菜单, 2按钮
     */
    @Column(name = "`resource_type`")
    private Integer resourceType;

    /**
     * 父级资源id
     */
    @Column(name = "`parent_id`")
    private Integer parentId;

    /**
     * 顺序
     */
    @Column(name = "`show_order`")
    private Integer showOrder;

    /**
     * 值
     */
    @Column(name = "`key_value`")
    private String keyValue;

    /**
     * 层级
     */
    @Column(name = "`level`")
    private Integer level;

    /**
     * 建立人id
     */
    @Column(name = "`creator`")
    private Integer creator;

    /**
     * 建立时间
     */
    @Column(name = "`create_time`")
    private Integer createTime;

    /**
     * 更新人id
     */
    @Column(name = "`updator`")
    private Integer updator;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Integer updateTime;

    /**
     * 是否有效, 0:有效, 1:无效
     */
    @Column(name = "`disabled`")
    private Integer disabled;

    /**
     * 状态, 0:正常,1:停用
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 资源描述
     */
    @Column(name = "`resource_desc`")
    private String resourceDesc;

    /**
     * 是否显示
     */
    @Column(name = "`is_show`")
    private Integer isShow;

    /**
     * 获取主键
     *
     * @return resource_id - 主键
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * 设置主键
     *
     * @param resourceId 主键
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 获取资源名称
     *
     * @return resource_name - 资源名称
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 设置资源名称
     *
     * @param resourceName 资源名称
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * 获取资源编码
     *
     * @return resource_code - 资源编码
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * 设置资源编码
     *
     * @param resourceCode 资源编码
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    /**
     * 获取资源访问路径
     *
     * @return url - 资源访问路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置资源访问路径
     *
     * @param url 资源访问路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取方法名称
     *
     * @return method_name - 方法名称
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置方法名称
     *
     * @param methodName 方法名称
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取资源类型, 1菜单, 2按钮
     *
     * @return resource_type - 资源类型, 1菜单, 2按钮
     */
    public Integer getResourceType() {
        return resourceType;
    }

    /**
     * 设置资源类型, 1菜单, 2按钮
     *
     * @param resourceType 资源类型, 1菜单, 2按钮
     */
    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取父级资源id
     *
     * @return parent_id - 父级资源id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级资源id
     *
     * @param parentId 父级资源id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取顺序
     *
     * @return show_order - 顺序
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    /**
     * 设置顺序
     *
     * @param showOrder 顺序
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    /**
     * 获取值
     *
     * @return key_value - 值
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * 设置值
     *
     * @param keyValue 值
     */
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(Integer level) {
        this.level = level;
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

    /**
     * 获取是否有效, 0:有效, 1:无效
     *
     * @return disabled - 是否有效, 0:有效, 1:无效
     */
    public Integer getDisabled() {
        return disabled;
    }

    /**
     * 设置是否有效, 0:有效, 1:无效
     *
     * @param disabled 是否有效, 0:有效, 1:无效
     */
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    /**
     * 获取状态, 0:正常,1:停用
     *
     * @return status - 状态, 0:正常,1:停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态, 0:正常,1:停用
     *
     * @param status 状态, 0:正常,1:停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取资源描述
     *
     * @return resource_desc - 资源描述
     */
    public String getResourceDesc() {
        return resourceDesc;
    }

    /**
     * 设置资源描述
     *
     * @param resourceDesc 资源描述
     */
    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc == null ? null : resourceDesc.trim();
    }

    /**
     * 获取是否显示
     *
     * @return is_show - 是否显示
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示
     *
     * @param isShow 是否显示
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}