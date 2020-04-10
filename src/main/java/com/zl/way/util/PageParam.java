package com.zl.way.util;

public class PageParam {
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        if (null == pageNum) {
            return DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (null == pageSize) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public static final class PageParamBuilder {
        private PageParam pageParam;

        private PageParamBuilder() {
            pageParam = new PageParam();
        }

        public static PageParamBuilder aPageParam() {
            return new PageParamBuilder();
        }

        public PageParamBuilder withPageNum(Integer pageNum) {
            pageParam.setPageNum(pageNum);
            return this;
        }

        public PageParamBuilder withPageSize(Integer pageSize) {
            pageParam.setPageSize(pageSize);
            return this;
        }

        public PageParam build() {
            return pageParam;
        }
    }
}
