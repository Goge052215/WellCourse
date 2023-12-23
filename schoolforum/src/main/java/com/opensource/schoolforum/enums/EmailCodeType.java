package com.opensource.schoolforum.enums;

public enum EmailCodeType {

    REGISTER("注册", 1), RETRIEVEPASSWORD("找回密码", 2),;

    // 成员变量
    private String name;
    private int type;
    // 构造方法
    private EmailCodeType(String name, int index) {
        this.name = name;
        this.type = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (EmailCodeType c : EmailCodeType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return type;
    }
    public void setIndex(int index) {
        this.type = index;
    }
}
