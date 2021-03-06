package com.zl.way.util;

public class WayPageRequest {

    public static final org.springframework.data.domain.PageRequest ONE = WayPageRequest.of(1, 1);
    public static final org.springframework.data.domain.PageRequest TWENTY = WayPageRequest.of(1, 20);

    public static final org.springframework.data.domain.PageRequest of(PageParam pageParam) {

        if (null == pageParam || null == pageParam.getPageNum() || null == pageParam.getPageSize()) {
            return null;
        }
        return of(pageParam.getPageNum(), pageParam.getPageSize());
    }

    public static final org.springframework.data.domain.PageRequest of(int page, int pageSize) {

        if (page < 1 || pageSize < 1) {
            return null;
        }
        return org.springframework.data.domain.PageRequest.of(page - 1, pageSize);
    }

}
