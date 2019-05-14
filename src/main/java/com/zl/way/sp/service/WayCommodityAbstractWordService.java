package com.zl.way.sp.service;

import com.zl.way.sp.model.WayCommodityAbstractWordBo;
import com.zl.way.sp.model.WayCommodityAbstractWordParam;
import com.zl.way.util.PageParam;

/**
 * sp抽象词服务
 *
 * @author xuzhongliang
 */
public interface WayCommodityAbstractWordService {

    /**
     * 根据条件查询抽象词
     *
     * @param param     入参
     * @param pageParam 分页入参
     * @return 抽象词结果
     */
    WayCommodityAbstractWordBo queryAbstractWord(WayCommodityAbstractWordParam param, PageParam pageParam);

}
