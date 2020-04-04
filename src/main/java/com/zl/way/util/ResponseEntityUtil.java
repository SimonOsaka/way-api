package com.zl.way.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    public static final String API_GENERATION = "Api-Generation";

    public static final String API_GENERATION_2 = "2";

    public static <T> ResponseEntity<T> sendResponseEntity(T t) {
        return sendResponseEntity(t, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> sendResponseEntity(T t, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCESS_CONTROL_EXPOSE_HEADERS, API_GENERATION);
        httpHeaders.add(API_GENERATION, API_GENERATION_2);
        return new ResponseEntity(t, httpHeaders, status);
    }
}
