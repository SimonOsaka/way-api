package com.zl.way.util;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BeanMapper {

    private final static Mapper dozerBean = DozerBeanMapperBuilder.buildDefault();

    public static <T> T map(Object source, Class<T> destinationClass) {

        if (source == null) {
            return null;
        }
        return dozerBean.map(source, destinationClass);
    }

    public static <T> List<T> mapAsList(Collection collection, Class<T> destinationClass) {

        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }

        List<T> destinationlist = new ArrayList<>(collection.size());
        for (Object object : collection) {
            T destinationObject = dozerBean.map(object, destinationClass);
            destinationlist.add(destinationObject);
        }
        return destinationlist;
    }
}
