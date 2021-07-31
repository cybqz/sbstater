package com.cyb.common.enums;

import java.util.Optional;

/**
 * @author CYB
 */
public class BooleanMapperStructUtil {

    public BooleanEnum integerToEnum(Integer code){
        Optional<BooleanEnum> optional = BooleanEnum.of(code);
        return null==optional?null:optional.get();
    }

    public Integer enumToInteger(BooleanEnum enums){
        return enums==null?null:enums.getCode();
    }
}
