package com.zl.way.util;

import com.zl.way.base.BaseEnum;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EnumUtil {

    public static <E extends Enum<E>> E getEnumByValue(byte value, Class<E> eClass) {

        List<E> enums = EnumUtils.getEnumList(eClass);
        for (E e : enums) {
            if (((BaseEnum)e).getValue() == value) {
                return e;
            }
        }

        return null;
    }

    public static <E extends Enum<E>> String getDescByValue(byte value, Class<E> eClass) {

        E e = getEnumByValue(value, eClass);
        if (null == e) {
            return StringUtils.EMPTY;
        }

        return ((BaseEnum)e).getDesc();
    }

}
