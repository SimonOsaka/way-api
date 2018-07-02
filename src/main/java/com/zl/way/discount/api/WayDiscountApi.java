package com.zl.way.discount.api;

import com.zl.way.discount.api.model.WayDiscountRequest;
import com.zl.way.discount.api.model.WayDiscountResponse;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.apache.commons.collections4.CollectionUtils;
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

		PageParam pageParam = new PageParam();
		pageParam.setPageNum(wayDiscountRequest.getPageNum());
		pageParam.setPageSize(wayDiscountRequest.getPageSize());

		List<WayDiscountBo> wayDiscountBoList = wayDiscountService.selectByCondition(wayDiscountParam, pageParam);
		if (CollectionUtils.isEmpty(wayDiscountBoList)) {
			return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
		}
		List<WayDiscountResponse> wayDiscountResponseList = BeanMapper
				.mapAsList(wayDiscountBoList, WayDiscountResponse.class);
		return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponseList);
	}

	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	public ResponseResult<WayDiscountResponse> getDiscountDetail(WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = new WayDiscountParam();
		wayDiscountParam.setDiscountId(wayDiscountRequest.getDiscountId());

		PageParam pageParam = new PageParam();
		pageParam.setPageNum(1);
		pageParam.setPageSize(1);

		List<WayDiscountBo> wayDiscountBoList = wayDiscountService.selectByCondition(wayDiscountParam, pageParam);
		if (CollectionUtils.isEmpty(wayDiscountBoList)) {
			return ResponseResultUtil.wrapSuccessResponseResult(null);
		}

		WayDiscountBo wayDiscountBo = wayDiscountBoList.get(0);
		WayDiscountResponse wayDiscountResponse = BeanMapper.map(wayDiscountBo, WayDiscountResponse.class);
		return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponse);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseResult<WayDiscountResponse> createDiscount(@RequestBody WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = BeanMapper.map(wayDiscountRequest, WayDiscountParam.class);
		wayDiscountService.createDiscount(wayDiscountParam);

		return ResponseResultUtil.wrapSuccessResponseResult(null);
	}

	@RequestMapping(value = "/real/increate", method = RequestMethod.POST)
	public ResponseResult<WayDiscountResponse> increateDiscountReal(
			@RequestBody WayDiscountRequest wayDiscountRequest) {

		WayDiscountParam wayDiscountParam = BeanMapper.map(wayDiscountRequest, WayDiscountParam.class);
		wayDiscountService.increateReal(wayDiscountParam.getDiscountId());

		return ResponseResultUtil.wrapSuccessResponseResult(null);

	}

}
