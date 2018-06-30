package com.zl.way.util;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanMapper {
    private final static Mapper dozerBean = DozerBeanMapperBuilder.buildDefault();

    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozerBean.map(source, destinationClass);
    }

    public static <T> List<T> mapAsList(Collection collection, Class<T> destinationClass) {
        List<T> destinationlist = new ArrayList<>(collection.size());
        for (Object object : collection) {
            T destinationObject = dozerBean.map(object, destinationClass);
            destinationlist.add(destinationObject);
        }
        return destinationlist;
    }
}
