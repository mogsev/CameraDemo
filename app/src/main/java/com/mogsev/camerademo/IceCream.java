package com.mogsev.camerademo;

/**
 * Created by zhenya on 13.08.2015.
 */
public class IceCream {
    private String code;
    private String name;

    public IceCream(String code, String name) {
        if (code != null) {
            this.code = code;
        }
        if (name != null) {
            this.name = name;
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
