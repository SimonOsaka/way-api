package com.zl.way.util;

import java.math.BigDecimal;

public final class GeoUtil {

    public static BigDecimal getDistance(BigDecimal clientLng, BigDecimal clientLat, BigDecimal shopLng,
                                         BigDecimal shopLat) {
        double distance = getDistance(clientLng.doubleValue(), clientLat.doubleValue(), shopLng.doubleValue(), shopLat.doubleValue());
        return new BigDecimal(distance);
    }

    /**
     * @param lng1 经度1
     * @param lat1 维度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static String getDistanceDesc(double distance) {
        if (distance > 1000) {
            double km = Math.round(distance / 100d);
            return km / 10d + "km";
        }
        return Math.round(distance) + "m";
    }


    public static void main(String[] args) {
        double dt = getDistance(116.695289, 39.869395, 116.687676, 39.872718);
        System.out.println(dt);

        String dtDesc = getDistanceDesc(dt);
        System.out.println(dtDesc);

        dt = getDistance(116.695289, 39.869395, 116.430984, 39.800054);
        System.out.println(dt);

        dtDesc = getDistanceDesc(dt);
        System.out.println(dtDesc);

        dt = getDistance(116.695289, 39.869395, 116.495375, 39.971021);
        System.out.println(dt);

        dtDesc = getDistanceDesc(dt);
        System.out.println(dtDesc);

        dt = getDistance(102.221415, 31.900402, 102.221374, 31.899792);
        System.out.println(dt);

        dtDesc = getDistanceDesc(dt);
        System.out.println(dtDesc);
    }
}
