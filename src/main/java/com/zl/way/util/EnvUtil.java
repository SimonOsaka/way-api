package com.zl.way.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvUtil {

	private final static Logger logger = LoggerFactory.getLogger(EnvUtil.class);

	private static final String ENV = System.getenv("spring.profiles.active");

	public static final String getAppEnv() {
		if (StringUtils.isNotBlank(ENV)) {
			return ENV;
		}

		//		Map<String, String> envMap = System.getenv();
		//		Set<String> envKeySets = envMap.keySet();
		//		for (String key : envKeySets) {
		//			if (envMap.containsKey(key)) {
		//				System.out.println("env变量key=" + key + "，value=" + envMap.get(key));
		//			}
		//		}
		//
		//		Set<Object> keySets = System.getProperties().keySet();
		//		for (Object key : keySets) {
		//			if (System.getProperties().containsKey(key)) {
		//				Object value = System.getProperties().get(key);
		//				System.out.println("property变量key=" + key + "，value=" + value);
		//			}
		//		}

		String prop = System.getProperty("spring.profiles.active", "development");

		return prop;
	}
}
