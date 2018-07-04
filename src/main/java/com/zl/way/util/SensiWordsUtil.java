package com.zl.way.util;

import com.zl.way.sensitive.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;

public class SensiWordsUtil {

	public static final boolean isSensiWords(String sentence) {
		if (StringUtils.isBlank(sentence)) {
			return false;
		}

		// 使用默认单例（加载默认词典）
		SensitiveFilter filter = SensitiveFilter.DEFAULT;

		// 进行过滤
		String filted = filter.filter(sentence, '*');

		// 如果未过滤，则返回输入的String引用
		return !sentence.trim().equals(filted.trim());
	}

	public static final String filterSensiWords(String sentence) {
		if (StringUtils.isBlank(sentence)) {
			return sentence;
		}

		// 使用默认单例（加载默认词典）
		SensitiveFilter filter = SensitiveFilter.DEFAULT;

		// 进行过滤
		String filted = filter.filter(sentence, '*');

		// 如果未过滤，则返回输入的String引用
		if (!sentence.equals(filted)) {
			return filted;
		}

		return sentence;
	}

	public static void main(String[] args) {
		String sentence = "市长零市长食市长";
		String result = filterSensiWords(sentence);
		System.out.println(result);
	}
}
