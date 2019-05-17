package com.zl.way.mp.api;

import com.zl.way.mp.api.model.DashboardResp;
import com.zl.way.mp.model.UserLoginParam;
import com.zl.way.mp.service.UserService;
import com.zl.way.mp.service.WayCommodityService;
import com.zl.way.mp.service.WayShopService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("mpDashboardApi") @RequestMapping("/mp/dashboard") public class DashboardApi
    extends BaseRestController {

    @Autowired private UserService userService;

    @Autowired private WayShopService shopService;

    @Autowired private WayCommodityService commodityService;

    @PostMapping("/user/total") public ResponseResult<DashboardResp> totalUsers() {
        Long count = userService.queryUserLoginCount(new UserLoginParam());
        return ResponseResultUtil.wrapSuccessResponseResult(new DashboardResp(count.intValue(), null, null));
    }

    @PostMapping("/shop/total") public ResponseResult<DashboardResp> totalShops() {
        Long count = shopService.queryOnlineCount();
        return ResponseResultUtil.wrapSuccessResponseResult(new DashboardResp(null, count.intValue(), null));
    }

    @PostMapping("/commodity/total") public ResponseResult<DashboardResp> totalCommodities() {
        Long count = commodityService.queryOnlineCount();
        return ResponseResultUtil.wrapSuccessResponseResult(new DashboardResp(null, null, count.intValue()));
    }
}
