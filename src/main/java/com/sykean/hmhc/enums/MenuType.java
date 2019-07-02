package com.sykean.hmhc.enums;

public enum MenuType {

    /**
     * 目录
     */
    MENU("目录", 0),
    /**
     * 菜单
     */
    BUTTON("按钮", 1);

    private String name;

    private int value;

    MenuType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
