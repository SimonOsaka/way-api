package com.zl.way.discount.api;

import com.zl.way.discount.api.model.WayDiscountRequest;
import com.zl.way.discount.api.model.WayDiscountResponse;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/discount")
public class WayDiscountApi {


	@Autowired
	private WayDiscountService wayDiscountService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public ResponseResult<List<WayDiscountResponse>> queryDiscountList(
			@RequestBody WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = new WayDiscountParam();
		wayDiscountParam.setClientLng(wayDiscountRequest.getClientLng());
		wayDiscountParam.setClientLat(wayDiscountRequest.getClientLat());
		wayDiscountParam.setLimitTimeExpireEnable(Boolean.TRUE);
		wayDiscountParam.setCityCode(wayDiscountRequest.getCityCode());

		PageParam pageParam = new PageParam();
		pageParam.setPageNum(wayDiscountRequest.getPageNum());
		pageParam.setPageSize(wayDiscountRequest.getPageSize());

		List<WayDiscountBo> wayDiscountBoList = wayDiscountService
				.selectByCondition(wayDiscountParam, pageParam);
		if (CollectionUtils.isEmpty(wayDiscountBoList)) {
			return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
		}
		List<WayDiscountResponse> wayDiscountResponseList = BeanMapper
				.mapAsList(wayDiscountBoList, WayDiscountResponse.class);
		return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponseList);
	}

	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	public ResponseResult<WayDiscountResponse> getDiscountDetail(
			WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = new WayDiscountParam();
		wayDiscountParam.setDiscountId(wayDiscountRequest.getDiscountId());

		WayDiscountBo wayDiscountBo = wayDiscountService.selectOne(wayDiscountParam);
		if (null == wayDiscountBo) {
			return ResponseResultUtil.wrapSuccessResponseResult(null);
		}

		WayDiscountResponse wayDiscountResponse = BeanMapper
				.map(wayDiscountBo, WayDiscountResponse.class);
		return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponse);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseResult<WayDiscountResponse> createDiscount(
			@RequestBody WayDiscountRequest wayDiscountRequest) {

		if (StringUtils.isBlank(wayDiscountRequest.getCommodityName())) {

			return ResponseResultUtil.wrapWrongParamResponseResult("商品名称必填");
		}

		if (wayDiscountRequest.getCommodityName().length() > 100) {
			return ResponseResultUtil.wrapWrongParamResponseResult("商品名称超长[限制100字内]");
		}

		if (SensiWordsUtil.isSensiWords(wayDiscountRequest.getCommodityName())) {
			return ResponseResultUtil.wrapWrongParamResponseResult("商品名称包含敏感字");
		}

		WayDiscountParam wayDiscountParam = BeanMapper
				.map(wayDiscountRequest, WayDiscountParam.class);
		wayDiscountService.createDiscount(wayDiscountParam);

		return ResponseResultUtil.wrapSuccessResponseResult(null);
	}

	@RequestMapping(value = "/real/increate", method = RequestMethod.POST)
	public ResponseResult<WayDiscountResponse> increateDiscountReal(
			@RequestBody WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = BeanMapper
				.map(wayDiscountRequest, WayDiscountParam.class);
		wayDiscountService.increateReal(wayDiscountParam.getDiscountId());

		return ResponseResultUtil.wrapSuccessResponseResult(null);

	}

}
