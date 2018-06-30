package com.zl.way.shop.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestApi {

    private Logger logger = LoggerFactory.getLogger(TestApi.class);

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Map<String, String> rest(@RequestParam("a") String a) {
        logger.debug("ping ok..." + a);
        Map<String, String> map = new HashMap<>();
        map.put("ok", "ok好了" + a);
        return map;
    }
}
