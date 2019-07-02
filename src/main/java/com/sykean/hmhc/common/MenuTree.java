package com.sykean.hmhc.common;

import lombok.Data;

@Data
public class MenuTree<T> extends TreeNode<T> {
    /**
     * 显示节点文本
     */
    private String label;

    private String code;

    private String icon;

    private String path;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}