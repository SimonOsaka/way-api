package com.zl.way.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.discount.mapper.WayDiscountMapper;
import com.zl.way.discount.model.WayDiscount;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Component
public class FetchElemeShopJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayDiscountMapper discountMapper;

    private static OkHttpClient okHttpClient = null;

    //    @Scheduled(fixedDelay = 3600000)
    public void doJob() throws InterruptedException {

        logger.info("启动{}", DateTime.now());
        int offset = 0;
        do {
            logger.info("开始offset={}", offset);
            JSONArray shopListJsonArray = getShopListJson(offset);
            //        System.out.println(shopListJsonArray);

            if (null != shopListJsonArray && shopListJsonArray.size() > 0) {
                for (Object shopObject : shopListJsonArray) {
                    JSONObject shopJsonObject = (JSONObject) shopObject;
                    DiscountData data = new DiscountData();
                    String shopAddress = shopJsonObject.getString("address");
                    data.setShopAddress(shopAddress);
                    Long shopId = shopJsonObject.getLong("id");
                    BigDecimal shopLatitude = shopJsonObject.getBigDecimal("latitude");
                    data.setShopLatitude(shopLatitude);
                    BigDecimal shopLongitude = shopJsonObject.getBigDecimal("longitude");
                    data.setShopLongitude(shopLongitude);

                    try {
                        data.getCommodityJson(shopId);
                    } catch (Exception e) {
                        logger.warn("异常", e);
                        continue;
                    }
                    data.setCideCode("0451");
                    System.out.println(data);

                    discountMapper.insertSelective(convertWayDiscount(data));
                    Thread.sleep(RandomUtils.nextInt(1274, 3588));

                }

                offset = offset + 24;
            } else {
                break;
            }
        } while (true);
        logger.info("结束{}", DateTime.now());
    }

    private WayDiscount convertWayDiscount(DiscountData data) {

        WayDiscount discount = new WayDiscount();
        discount.setCommodityName(data.getCommodityName());
        discount.setCommodityCate(data.getCommodityCate());
        discount.setCommodityPrice(data.getPrice());
        discount.setCityCode(data.getCideCode());
        discount.setLimitTimeExpire(DateUtils.addDays(DateTime.now().toDate(), 7));
        discount.setShopLat(data.getShopLatitude());
        discount.setShopLng(data.getShopLongitude());
        discount.setShopPosition(data.getShopAddress());
        discount.setUserLoginId(1L);

        return discount;
    }

    private JSONArray getShopListJson(int offset) {

        String url = MessageFormat
                .format("https://www.ele.me/restapi/shopping/restaurants?extras%5B%5D=activities&geohash=yb4h6zdz12nc&latitude=45.789471&limit=24&longitude=126.687189&offset={0}&terminal=web",
                        offset);
        String shopListJson = getJson(url);
        return JSONArray.parseArray(shopListJson);
    }

    private String getJson(String url) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //创建Request
        Request request = new Request.Builder().url(url).header("upgrade-insecure-requests", "1")
                .header(":authority", "www.ele.me").header(":method", "GET").header("user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                .header("referer",
                        "https://www.ele.me/place/yb4h6zdz12nc?latitude=45.789471&longitude=126.687189")
                .header("cookie",
                        "ubt_ssid=oryjdhtgwua6mn0uemqgcvoaty40yads_2018-06-28; _utrace=be9ea835d03d4d9fa7263c1d8bfaef42_2018-06-28; track_id=1530331449|464b808db5e83dab28d4a81b8c2b98fffbda305431e61de259|e968f39e8ce9a1010e5ca11b4506e3e7; USERID=26768136; SID=xqJqady77mdbtXZ9BlU8aODZE1BO9ouCCmig")
                .build();

        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行同步请求
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                //得到返回结果
                String responseString = response.body().string();
                return responseString;
            }
        } catch (Exception e) {
        } finally {
            if (null != response) {
                response.close();
            }
        }

        return "";
    }

    private static synchronized OkHttpClient getInstance() {

        if (okHttpClient == null) {
            //加同步安全
            okHttpClient = new OkHttpClient.Builder()//构建器
                    .connectTimeout(15, TimeUnit.SECONDS)//连接超时
                    .writeTimeout(30, TimeUnit.SECONDS)//写入超时
                    .readTimeout(30, TimeUnit.SECONDS)//读取超时
                    .build();
        }

        return okHttpClient;
    }

    public static void main(String[] args) throws InterruptedException {

        new FetchElemeShopJob().doJob();

    }

    private class DiscountData {

        private String shopAddress;

        private BigDecimal shopLatitude;

        private BigDecimal shopLongitude;

        private String commodityName;

        private String commodityCate;

        private BigDecimal price;

        private String cideCode;

        public void getCommodityJson(Long shopId) throws Exception {

            try {
                String url = String
                        .format("https://www.ele.me/restapi/shopping/v2/menu?restaurant_id=%s&terminal=web",
                                shopId);
                String commodityListJson = getJson(url);
                JSONArray commodityJsonArray = JSONArray.parseArray(commodityListJson);
                if (null != commodityJsonArray && commodityJsonArray.size() > 0) {
                    JSONObject commodityJsonObject = commodityJsonArray.getJSONObject(0);
                    JSONArray foodsJsonArray = commodityJsonObject.getJSONArray("foods");
                    JSONObject hotJsonObject = foodsJsonArray.getJSONObject(0);
                    String commodityName = hotJsonObject.getString("name");
                    setCommodityName(commodityName);
                    String commodityCate = "others";
                    setCommodityCate(commodityCate);
                    BigDecimal price = hotJsonObject.getJSONArray("specfoods").getJSONObject(0)
                            .getBigDecimal("price");
                    setPrice(price);
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }

        public String getShopAddress() {

            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {

            this.shopAddress = shopAddress;
        }

        public BigDecimal getShopLatitude() {

            return shopLatitude;
        }

        public void setShopLatitude(BigDecimal shopLatitude) {

            this.shopLatitude = shopLatitude;
        }

        public BigDecimal getShopLongitude() {

            return shopLongitude;
        }

        public void setShopLongitude(BigDecimal shopLongitude) {

            this.shopLongitude = shopLongitude;
        }

        public String getCommodityName() {

            return commodityName;
        }

        public void setCommodityName(String commodityName) {

            this.commodityName = commodityName;
        }

        public String getCommodityCate() {

            return commodityCate;
        }

        public void setCommodityCate(String commodityCate) {

            this.commodityCate = commodityCate;
        }

        public BigDecimal getPrice() {

            return price;
        }

        public void setPrice(BigDecimal price) {

            this.price = price;
        }

        public String getCideCode() {

            return cideCode;
        }

        public void setCideCode(String cideCode) {

            this.cideCode = cideCode;
        }

        @Override
        public String toString() {

            return "DiscountData{" + "shopAddress='" + shopAddress + '\'' + ", shopLatitude="
                    + shopLatitude + ", shopLongitude=" + shopLongitude + ", commodityName='"
                    + commodityName + '\'' + ", commodityCate='" + commodityCate + '\'' + ", price="
                    + price + ", cideCode='" + cideCode + '\'' + '}';
        }
    }
}
