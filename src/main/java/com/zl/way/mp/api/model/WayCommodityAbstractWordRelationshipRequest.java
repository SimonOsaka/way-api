package com.zl.way.mp.api.model;

import java.util.List;

import com.zl.way.util.PageParam;

/**
 * @author xuzhongliang
 */
public class WayCommodityAbstractWordRelationshipRequest extends PageParam {
    private Integer id;
    private Integer abstractWordId;
    private List<Integer> abstractWordIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbstractWordId() {
        return abstractWordId;
    }

    public void setAbstractWordId(Integer abstractWordId) {
        this.abstractWordId = abstractWordId;
    }

    public List<Integer> getAbstractWordIdList() {
        return abstractWordIdList;
    }

    public void setAbstractWordIdList(List<Integer> abstractWordIdList) {
        this.abstractWordIdList = abstractWordIdList;
    }
}