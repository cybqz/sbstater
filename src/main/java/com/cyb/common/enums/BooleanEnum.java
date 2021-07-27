package com.cyb.common.enums;

import java.util.Optional;

/**
 * @author CYB
 */

public enum BooleanEnum implements BaseEnum {
    NO(0, "否"), YES(1, "是");

    private Integer code;
    private String name;

    BooleanEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static Optional<BooleanEnum> of(Integer code) {
        return null == code?Optional.ofNullable(null):Optional.ofNullable(BaseEnum.parseByCode(BooleanEnum.class, code));
    }
}
