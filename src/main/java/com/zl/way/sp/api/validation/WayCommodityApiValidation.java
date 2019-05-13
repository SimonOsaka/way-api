package com.zl.way.sp.api.validation;

import com.zl.way.sp.model.WayCommodityRequest;
import com.zl.way.util.SensiWordsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WayCommodityApiValidation {

    private WayCommodityRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayCommodityApiValidation(WayCommodityRequest request) {

        this.request = request;
    }

    public WayCommodityApiValidation commodityId() {

        if (null == request.getId()) {
            validationMessageList.add("商品id不能为空");
        }
        return this;
    }

    public WayCommodityApiValidation shopId() {

        if (null == request.getShopId()) {
            validationMessageList.add("商家id不能为空");
        }
        return this;
    }

    public WayCommodityApiValidation name() {

        if (StringUtils.isBlank(request.getName())) {
            validationMessageList.add("商品名称不能为空");
            return this;
        }

        if (SensiWordsUtil.isSensiWords(request.getName())) {
            String sensiWords = SensiWordsUtil.getSensiWords(request.getName());
            validationMessageList.add("商品名称包含敏感词汇[" + sensiWords + "]");
            return this;
        }

        if (StringUtils.length(request.getName()) > 100) {
            validationMessageList.add("商品名称最多100字");
            return this;
        }
        return this;
    }

    public WayCommodityApiValidation price() {

        if (null == request.getPrice()) {
            validationMessageList.add("商品价格不能为空");
            return this;
        }

        if (request.getPrice().compareTo(new BigDecimal("0.1")) < 0) {
            validationMessageList.add("商品价格不正确");
        }
        return this;
    }

    public WayCommodityApiValidation imgUrl() {

        if (CollectionUtils.isEmpty(request.getImgUrlList())) {
            validationMessageList.add("商品图片不能为空");
            return this;
        }

        if (CollectionUtils.size(request.getImgUrlList()) > 5) {
            validationMessageList.add("商品图片最多5张");
            return this;
        }
        return this;
    }

    public WayCommodityApiValidation abstractWordId() {
        if (null == request.getAbstractWordId()) {
            validationMessageList.add("关联词不能为空");
            return this;
        }

        if (request.getAbstractWordId() < 1) {
            validationMessageList.add("关联词不正确");
        }
        return this;
    }

    public boolean hasErrors() {

        return !validationMessageList.isEmpty();
    }

    public List<String> getErrors() {

        return validationMessageList;
    }

}
