package com.cyb.authority.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum SexEnum {

    MAN(1, "男"), WOMAN(0, "女");

    @EnumValue
    private Integer code;
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static SexEnum getByCode(int code) {
        for (SexEnum value : SexEnum.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
