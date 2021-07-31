package com.cyb.common.enums;

import javax.persistence.AttributeConverter;
import java.util.Optional;

/**
 *BooleanEnum转换实现类
 * @author CYB
 */
public class BooleanEnumAttConverter implements AttributeConverter<BooleanEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BooleanEnum booleanEnum) {
        return booleanEnum==null?null:booleanEnum.getCode();
    }

    @Override
    public BooleanEnum convertToEntityAttribute(Integer code) {
        Optional<BooleanEnum> optional = BooleanEnum.of(code);
        return null==optional?null:optional.get();
    }
}
