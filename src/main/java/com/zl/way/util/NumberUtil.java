package com.zl.way.util;

public final class NumberUtil {

    /**
     * 不是Long类型主键
     *
     * @param key
     * @return
     */
    public static final boolean isNotLongKey(Long key) {
        return (null == key || key < 1);
    }

    public static final boolean isLongKey(Long key) {
        return (null != key && key > 0);
    }
}
