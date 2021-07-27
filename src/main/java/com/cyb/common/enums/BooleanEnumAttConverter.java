package com.cyb.common.enums;

import javax.persistence.AttributeConverter;

/**
 *BooleanEnum转换实现类
 * @author CYB
 */
public class BooleanEnumAttConverter implements AttributeConverter<BooleanEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BooleanEnum booleanEnum) {
        return booleanEnum.getCode();
    }

    @Override
    public BooleanEnum convertToEntityAttribute(Integer code) {
        return BooleanEnum.of(code).get();
    }
}
