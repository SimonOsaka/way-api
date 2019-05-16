package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.sp.model.WayCommodityAbstractWord;
import com.zl.way.sp.model.WayCommodityAbstractWordBo;
import com.zl.way.sp.model.WayCommodityAbstractWordCondition;
import com.zl.way.sp.model.WayCommodityAbstractWordParam;
import com.zl.way.sp.service.WayCommodityAbstractWordService;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("spWayCommodityAbstractWordService") public class WayCommodityAbstractWordServiceImpl
    implements WayCommodityAbstractWordService {
    private WayCommodityAbstractWordMapper commodityAbstractWordMapper;

    @Autowired public WayCommodityAbstractWordServiceImpl(WayCommodityAbstractWordMapper commodityAbstractWordMapper) {
        this.commodityAbstractWordMapper = commodityAbstractWordMapper;
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodityAbstractWordBo queryAbstractWord(WayCommodityAbstractWordParam param, PageParam pageParam) {

        WayCommodityAbstractWordCondition condition = new WayCommodityAbstractWordCondition();
        condition.setShopCateLeafId(param.getShopCateLeafId());
        condition.setPid(param.getPid());
        condition.setPathPid(param.getPathPid());
        condition.setLeaf(param.getLeaf());
        List<WayCommodityAbstractWord> commodityAbstractWordList =
            commodityAbstractWordMapper.selectByCondition(condition, WayPageRequest.of(pageParam));

        WayCommodityAbstractWordBo commodityAbstractWordBo = new WayCommodityAbstractWordBo();
        commodityAbstractWordBo.setCommodityAbstractWordList(commodityAbstractWordList);
        return commodityAbstractWordBo;
    }

}
