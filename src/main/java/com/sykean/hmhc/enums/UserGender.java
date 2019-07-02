package com.sykean.hmhc.enums;

public enum UserGender {
    /**
     * 用户男
     */
    MALE("男", "0"),
    /**
     * 用户女
     */
    FEMALE("女", "1");

    private String name;

    private String value;

    UserGender(String name, String value) {
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
