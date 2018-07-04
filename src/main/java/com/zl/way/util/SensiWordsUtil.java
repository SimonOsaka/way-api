package com.zl.way.util;

import com.zl.way.sensitive.stop.WordFilter;
import org.apache.commons.lang3.StringUtils;

public class SensiWordsUtil {

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

	public static void main(String[] args) {
		String sentence = "法@!轮!%%%功市长零市长食市长毛登    辉妹妹";
		String result = filterSensiWords(sentence);
		System.out.println(result);
		System.out.println(WordFilter.doFilter(sentence));

		String s = "你是逗比吗？ｆｕｃｋ！fUcK,你竟然用法轮功，法@!轮!%%%功";
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
	}
}
