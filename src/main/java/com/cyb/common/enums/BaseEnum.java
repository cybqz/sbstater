package com.cyb.common.enums;

/**
 * 基础枚举类
 * @author CYB
 */
public interface BaseEnum<T extends Enum<T> & BaseEnum<T>> {

    /**
     * 获取code
     * @return
     */
    Integer getCode();

    /**
     * 获取name
     * @return
     */
    String getName();

    /**
     * 根据code值查询枚举值
     * @param cls
     * @param code
     * @param <T>
     * @return
     */
    static <T extends Enum<T> & BaseEnum<T>> T parseByCode(Class<T> cls, Integer code) {
        Enum[] var2 = (Enum[])cls.getEnumConstants();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            T t = (T) var2[var4];
            if (((BaseEnum)t).getCode().equals(code)) {
                return t;
            }
        }

        return null;
    }
}
