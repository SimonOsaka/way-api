package com.zl.way.util;

import java.util.Objects;

public class XssUtil {

    public static String cleanXSS(String value) {

        if (Objects.isNull(value)) {
            return value;
        }
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    public static void main(String[] args) {

        String str = "<script src='sss.js'></script>";
        System.out.println(cleanXSS(str));

        str = "<img src='ss.jpg'></img>";
        System.out.println(cleanXSS(str));

    }
}
