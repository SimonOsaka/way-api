package com.zl.way.ios;

import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ios")
public class AppVersionController {

    private static final String CURRENT_APP_VERSION = "1.0.0";

    private static final String OPERATION_FORCE = "force";

    private static final String OPERATION_NOTICE = "notice";

    private static final String OPERATION_NONE = "none";

    private static final List<String> COMMENT_LIST = new ArrayList<>();

    private static final String APPSTORE_URL = "itms-apps://itunes.apple.com/cn/app/id392899425?mt=8";

    static {
        COMMENT_LIST.add("1.新增用户头像修改");
        COMMENT_LIST.add("2.增加优惠方式");
        COMMENT_LIST.add("3.优化app性能");
    }

    @GetMapping("/app/version")
    public ResponseResult<AppVersionResponse> checkAppVersion(AppVersionRequest request) {

        if (StringUtils.isBlank(request.getAppVersion())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("沒有提供appVersion");
        }

        final String appVersion = request.getAppVersion();
        String[] appVersionArray = StringUtils.split(request.getAppVersion(), ".");
        if (appVersionArray.length != 3) {
            return ResponseResultUtil.wrapWrongParamResponseResult("appVersion格式不正确");
        }

        AppVersionResponse response = new AppVersionResponse();
        if (!CURRENT_APP_VERSION.equalsIgnoreCase(appVersion)) {
            response.setOperation(OPERATION_NOTICE);
            response.setCommentList(COMMENT_LIST);
            response.setNewAppVersion(CURRENT_APP_VERSION);
            response.setAppStoreUrl(APPSTORE_URL);
            return ResponseResultUtil.wrapSuccessResponseResult(response);
        }

        response.setOperation(OPERATION_NONE);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

}
