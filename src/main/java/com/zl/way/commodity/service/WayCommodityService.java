package com.zl.way.commodity.service;

import java.util.List;

import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.util.PageParam;

/**
 * 商品服务接口
 * 
 * @author xuzhongliang
 */
public interface WayCommodityService {

    /**
     * 获取商品信息
     *
     * @param id
     * @return
     */
    WayCommodity getCommodityDetail(Long id);

    /**
     * 根据条件查询商品信息
     *
     * @param wayCommodityParam
     * @param pageParam
     * @return
     */
    List<WayCommodityBo> pageCommodityByCondition(WayCommodityParam wayCommodityParam, PageParam pageParam);

    /**
     * 根据商品id，查询商品抽象词关联的商品集合
     * 
     * @param wayCommodityParam
     * @return 20个商品集合
     */
    List<WayCommodityBo> queryRelationCommodity(WayCommodityParam wayCommodityParam);
}
