package com.zl.way.util;

import com.zl.way.sensitive.stop.WordFilter;
import org.apache.commons.lang3.StringUtils;

public class SensiWordsUtil {

    private static final String CHINESE_COMMA = "，";

    private static final String MULTI_COMMA_REGEX = "，{2,}";

    public static final boolean isSensiWords(String sentence) {

        if (StringUtils.isBlank(sentence)) {
            return false;
        }

        return WordFilter.isContains(sentence);
    }

    public static final String filterSensiWords(String sentence) {

        if (StringUtils.isBlank(sentence)) {
            return sentence;
        }

        return WordFilter.doFilter(sentence);
    }

    public static final String getSensiWords(String sentence) {

        if (StringUtils.isBlank(sentence)) {
            return sentence;
        }
        char[] sc = sentence.toCharArray();
        String filterString = WordFilter.doFilter(sentence);

        char[] fc = filterString.toCharArray();
        for (int i = 0; i < fc.length; i++) {
            char c = fc[i];
            if (c != '*') {
                sc[i] = CHINESE_COMMA.charAt(0);
            }
        }

        String result = String.valueOf(sc).replaceAll(MULTI_COMMA_REGEX, CHINESE_COMMA);
        result = result.startsWith(CHINESE_COMMA) ? result.substring(1) : result;
        return result.endsWith(CHINESE_COMMA) ? result.substring(0, result.length() - 1) : result;
    }

    public static void main(String[] args) {

        String sentence = "法@!轮!%%%功市长零市长食市长毛登    辉妹妹";
        String result = filterSensiWords(sentence);
        System.out.println(result);
        System.out.println(WordFilter.doFilter(sentence));

        String s = "你是逗比吗？ｆｕｃｋ！fUcK,你竟然用法轮功，法@!轮!%%%功哈哈";
        System.out.println("解析问题： " + s);
        System.out.println("解析字数 : " + s.length());
        String re;
        long nano = System.nanoTime();
        re = WordFilter.doFilter(s);
        nano = (System.nanoTime() - nano);
        System.out.println("解析时间 : " + nano + "ns");
        System.out.println("解析时间 : " + nano / 1000000 + "ms");
        System.out.println(re);
        System.out.println();

        nano = System.nanoTime();
        System.out.println("是否包含敏感词： " + WordFilter.isContains(s));
        nano = (System.nanoTime() - nano);
        System.out.println("解析时间 : " + nano + "ns");
        System.out.println("解析时间 : " + nano / 1000000 + "ms");

        System.out.println(WordFilter.doFilter("爱情的午餐"));

        System.out.println(getSensiWords("你是逗比吗？ｆｕｃｋ！fUcK,你竟然用法轮功，法@!轮!%%%功"));

        String d = "最好的服装";
        System.out.println(getSensiWords(d));
    }
}
