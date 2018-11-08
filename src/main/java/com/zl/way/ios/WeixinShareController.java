package com.zl.way.ios;

import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.service.WayCommodityService;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixin")
public class WeixinShareController {

    @Autowired
    private WayCommodityService commodityService;

    @Autowired
    private WayShopService shopService;

    @Autowired
    private WayDiscountService discountService;

    @PostMapping("/webpage/commodity")
    public ResponseResult<WeixinShareResponse> shareCommodityWebpage(
            @RequestBody WeixinShareRequest request) {

        Long commodityId = request.getCommodityId();
        if (null == commodityId || commodityId < 0) {
            return ResponseResultUtil.wrapWrongParamResponseResult("商品详情id不正确");
        }

        if (StringUtils.isBlank(request.getShareType()) || (
                !StringUtils.equalsIgnoreCase(request.getShareType(), "session") && !StringUtils
                        .equalsIgnoreCase(request.getShareType(), "timeline"))) {
            return ResponseResultUtil.wrapWrongParamResponseResult("分享类型不正确");
        }

        WayCommodity commodity = commodityService.getCommodityDetail(commodityId);
        if (null == commodity) {
            return ResponseResultUtil.wrapWrongParamResponseResult("商品详情不存在");
        }

        WayShop shop = shopService.getPromoShopDetail(commodity.getShopId());
        WeixinShareResponse response = new WeixinShareResponse();
        response.setDesc(shop.getShopAddress());
        response.setImageUrl(commodity.getImgUrl());
        response.setShareType(request.getShareType());
        response.setTitle(commodity.getName());
        response.setWebpageUrl(
                "http://h5.jicu.vip/views/commodity/detail.html?cid=" + commodity.getId());

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/webpage/discount")
    public ResponseResult<WeixinShareResponse> shareDiscountWebpage(
            @RequestBody WeixinShareRequest request) {

        Long discountId = request.getDiscountId();
        if (null == discountId || discountId < 0) {
            return ResponseResultUtil.wrapWrongParamResponseResult("优惠详情id不正确");
        }

        if (StringUtils.isBlank(request.getShareType()) || (
                !StringUtils.equalsIgnoreCase(request.getShareType(), "session") && !StringUtils
                        .equalsIgnoreCase(request.getShareType(), "timeline"))) {
            return ResponseResultUtil.wrapWrongParamResponseResult("分享类型不正确");
        }

        WayDiscountParam discountParam = new WayDiscountParam();
        discountParam.setDiscountId(discountId);
        WayDiscountBo discountBo = discountService.selectOne(discountParam);
        if (null == discountBo) {
            return ResponseResultUtil.wrapWrongParamResponseResult("优惠详情不存在");
        }

        WeixinShareResponse response = new WeixinShareResponse();
        response.setDesc(discountBo.getShopPosition());
        response.setImageUrl(discountBo.getCommodityImageUrl());
        response.setShareType(request.getShareType());
        response.setTitle(
                discountBo.getCommodityName() + " " + discountBo.getCommodityPrice() + "元");
        response.setWebpageUrl(
                "http://h5.jicu.vip/views/discount/detail.html?discountId=" + discountBo.getId());

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
