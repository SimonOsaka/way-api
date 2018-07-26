package com.zl.way.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public final class TokenUtil {

    private static final String TOKEN_SECRET = "11:11";

    public static final String getToken(String str) {

        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }

        return md5Token(str);
    }

    public static final boolean validToken(String str, String token) {

        if (StringUtils.isBlank(str) || StringUtils.isBlank(token)) {
            return false;
        }

        if (md5Token(str).equals(token)) {
            return true;
        }

        return false;
    }

    public static String md5Token(String str) {

        byte[] strBytes = (str + TOKEN_SECRET).getBytes();
        return DigestUtils.md5DigestAsHex(strBytes);
    }

    public static void main(String[] args) {

        String str = "8";
        String token = getToken(str);
        System.out.println(token);

        boolean pass = validToken(str, token);
        System.out.println(pass);
    }
}
