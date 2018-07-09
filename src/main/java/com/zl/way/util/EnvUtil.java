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

		String prop = System.getProperty("spring.profiles.active", "development");

		return prop;
	}
}
