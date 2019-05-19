package com.zl.way.sp.api.validation;

import com.zl.way.sp.model.WayDiscountRequest;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WayDiscountApiValidation {

    private WayDiscountRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayDiscountApiValidation(WayDiscountRequest request) {

        this.request = request;
    }

    public WayDiscountApiValidation discountId() {

        if (null == request.getId()) {
            validationMessageList.add("优惠id不能为空");
            return this;
        }
        return this;
    }

    public WayDiscountApiValidation discountCommodityPrice() {

        if (null == request.getCommodityPrice() || request.getCommodityPrice().compareTo(BigDecimal.ZERO) <= 0) {
            validationMessageList.add("优惠价格不正确");
            return this;
        }
        return this;
    }

    public WayDiscountApiValidation commodityId() {

        if (null == request.getCommodityId() || request.getCommodityId() <= 0L) {
            validationMessageList.add("商品ID不正确");
            return this;
        }

        return this;
    }

    public WayDiscountApiValidation discountLimitTimeExpire() {

        if (null == request.getLimitTimeExpire()) {
            validationMessageList.add("优惠过期时间不能为空");
            return this;
        }

        return this;
    }

    public WayDiscountApiValidation discountCommodityCate() {

        if (StringUtils.isBlank(request.getCommodityCate())) {
            validationMessageList.add("优惠分类不能为空");
            return this;
        }

        if (!request.getCommodityCate().equalsIgnoreCase("clothes")
            && !request.getCommodityCate().equalsIgnoreCase("drinks")
            && !request.getCommodityCate().equalsIgnoreCase("others")
            && !request.getCommodityCate().equalsIgnoreCase("snacks")
            && !request.getCommodityCate().equalsIgnoreCase("tools")
            && !request.getCommodityCate().equalsIgnoreCase("vegetables")) {
            validationMessageList.add("优惠分类不正确");
            return this;
        }

        return this;
    }

    public boolean hasErrors() {

        return !validationMessageList.isEmpty();
    }

    public List<String> getErrors() {

        return validationMessageList;
    }

    public String getFirstError() {

        if (validationMessageList.isEmpty()) {
            return StringUtils.EMPTY;
        }

        return validationMessageList.get(0);
    }

}
