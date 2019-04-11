package com.telfa.andrei.vo;

import java.util.List;

public class ResourceVo {
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<ResourceVo> children;


    public ResourceVo(){}

    public ResourceVo(String title, String icon, String href, Boolean spread) {
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    public ResourceVo(String title, String icon, String href, Boolean spread, List<ResourceVo> children) {
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ResourceVo(String title) {
        this.title = title;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<ResourceVo> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceVo> children) {
        this.children = children;
    }
}
