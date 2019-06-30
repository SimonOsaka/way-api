package com.zl.way.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.SymbolConstants;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapSearchTextModel;
import com.zl.way.amap.model.AMapSearchTextRequest;
import com.zl.way.amap.model.AMapSearchTextResponse;
import com.zl.way.amap.service.AMapSearchTextService;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.sp.model.WayShopBo;
import com.zl.way.sp.model.WayShopParam;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.sp.service.WayShopService;

@Component
public class FetchAmapPlaceTextJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AMapSearchTextService aMapSearchTextService;

    @Autowired
    private WayShopService shopService;

    @Autowired
    private WayCommodityService commodityService;

    private static final String[] PROVINCE_CODE = {"370700", "130600", "321100", "321000", "450300", "130200", "460200",
        "330500", "150100", "131000", "410300", "371000", "320900", "371300", "440700", "440500", "321200", "350600",
        "130400", "370800", "340200", "370300", "450200", "510700", "440800", "210300", "360700", "230600", "420500",
        "150200", "610400", "130300", "430200", "350300", "220200", "320800", "441200", "350900", "430400", "350700",
        "320700", "210600", "530700", "445200", "222400", "330900", "360400", "350800", "130900", "210400", "420600",
        "361100", "210800", "350400", "340300", "331100", "430600", "441800", "421000", "370900", "330800", "211100",
        "370500", "411300", "340500", "511300", "630100", "420900", "230200"};
    /*{"0451", "0431", "024", "0471", "0311", "0531",
            "0351", "029", "0371", "025", "0551", "027", "028", "0871", "0851", "0731", "0791",
            "0571", "0591", "020", "0771", "0898", "0951", "0931", "0971"};*/

    // @Scheduled(fixedDelay = 3600000 * 24)
    public void doJob() throws AMapException, InterruptedException {

        logger.info("启动{}", DateTime.now());
        for (String code : PROVINCE_CODE) {
            logger.info("省份code={}", code);
            int page = 1;
            while (true) {
                List<AMapSearchTextModel> textModelList = this.queryPlaceTextList(page, code);
                if (CollectionUtils.isEmpty(textModelList)) {
                    break;
                }
                List<WayShopParam> shopParamList = this.shopAdapter(textModelList);
                this.addShop(shopParamList);
                page++;

                Thread.sleep(RandomUtils.nextInt(2759, 9583));
            }
        }
        logger.info("结束{}", DateTime.now());
    }

    private List<AMapSearchTextModel> queryPlaceTextList(int page, String provinceCode) throws AMapException {

        AMapSearchTextRequest aMapSearchTextRequest = new AMapSearchTextRequest();
        aMapSearchTextRequest.setCity(provinceCode);
        aMapSearchTextRequest.setTypeList(Arrays.asList("婴儿服务场所", "婴儿游泳馆"));
        aMapSearchTextRequest.setOffset(20);
        aMapSearchTextRequest.setPage(page);
        AMapSearchTextResponse aMapSearchTextResponse = aMapSearchTextService.searchText(aMapSearchTextRequest);
        if (aMapSearchTextResponse.getCode() == 200) {
            return aMapSearchTextResponse.getSearchTextModelList();
        }

        return Collections.emptyList();
    }

    private List<WayShopParam> shopAdapter(List<AMapSearchTextModel> textModelList) {

        List<WayShopParam> shopParamList = new ArrayList<>();
        for (int i = 0; i < textModelList.size(); i++) {
            AMapSearchTextModel model = textModelList.get(i);
            WayShopParam shopParam = new WayShopParam();
            shopParam.setShopName(model.getName());
            shopParam.setShopAddress(model.getpName() + model.getCityName() + model.getAdName() + model.getAddress());
            shopParam.setShopTel(
                StringUtils.isNotBlank(model.getTel()) ? model.getTel().split(SymbolConstants.SEMICOLON)[0] : null);
            shopParam.setShopBusinessTime1("00:00-23:45");
            shopParam.setShopCateLeafId(46);
            String longitude = model.getLocation().split(SymbolConstants.COMMA)[0];
            shopParam.setShopLng(new BigDecimal(longitude));
            String latitude = model.getLocation().split(SymbolConstants.COMMA)[1];
            shopParam.setShopLat(new BigDecimal(latitude));
            shopParam.setAdCode(model.getAdCode());
            shopParam.setCityCode(model.getCityCode());
            shopParam.setShopLogoUrl("http://static.jicu.vip/blank_trans.jpg");
            try {
                shopParam.setShopPinyin(
                    PinyinHelper.convertToPinyinString(model.getName(), StringUtils.EMPTY, PinyinFormat.WITHOUT_TONE));
                shopParam.setShopPy(PinyinHelper.getShortPinyin(model.getName()));
            } catch (PinyinException e) {
            }
            shopParamList.add(shopParam);
        }

        return shopParamList;
    }

    private void addShop(List<WayShopParam> spShopParamList) {

        for (int i = 0; i < spShopParamList.size(); i++) {
            WayShopParam shopParam = spShopParamList.get(i);
            WayShopBo shopBo = shopService.createShop(shopParam);
            Long shopId = shopBo.getId();
            this.addCommodity(shopId);
        }
    }

    private void addCommodity(Long shopId) {

        WayCommodityParam wayCommodityParam = new WayCommodityParam();
        wayCommodityParam.setShopId(shopId);
        wayCommodityParam.setName("儿童服装");
        wayCommodityParam.setImgUrlList(Arrays.asList("http://static.jicu.vip/kid_clothes.jpg"));
        commodityService.createCommodity(wayCommodityParam);
    }

    public static void main(String[] args) throws AMapException, InterruptedException {

        new FetchAmapPlaceTextJob().doJob();

    }

}
