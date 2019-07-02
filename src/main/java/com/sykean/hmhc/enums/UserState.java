package com.sykean.hmhc.enums;

public enum UserState {
    /**
     * 用户在用
     */
    NORMAL("在用", "1"),
    /**
     * 用户停用
     */
    STOP("停用", "0");

    private String name;

    private String value;

    UserState(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
