package com.w77996.straw.core.constant;

/**
 * @description:
 * @author: straw
 **/
public enum PlatformConstant {

    /**
     * android平台
     */
    Android("Android"),

    /**
     * 小程序平台
     */
    MiniApp("MiniApp");

    PlatformConstant(String value) {
        this.value = value;
    }

    /**
     * 平台类型
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
