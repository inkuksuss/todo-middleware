package com.project.todo.common.utils;

import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class URLUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String createUrl(String url, Map<String, String> paramMap) {
        Assert.notNull(url, "url cannot be null");
        Assert.notNull(paramMap, "paramMap cannot be null");

        Object[] keys = paramMap.keySet().toArray();
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");

        try {
            for (int i = 0; i < paramMap.size(); i++) {
                String key = (String) keys[i];
                String value = paramMap.get(key);

                sb.append(URLEncoder.encode(key, DEFAULT_CHARSET)).append("=").append(URLEncoder.encode(value, DEFAULT_CHARSET));

                if (paramMap.size() - i > 1) {
                    sb.append("&");
                }

            }
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        return sb.toString();
    }
}
