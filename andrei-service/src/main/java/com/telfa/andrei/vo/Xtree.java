package com.telfa.andrei.vo;

import java.util.List;


/**
 *属性菜单对象
 * @since 1.8
 */
public class Xtree {
    private String title;
    private String value;
    private boolean checked;
    private List<Xtree> data;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Xtree> getData() {
        return data;
    }

    public void setData(List<Xtree> data) {
        this.data = data;
    }
}
