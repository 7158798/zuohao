package com.szzc.api.three.utils.duiba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class AssembleTool {

    private static final Logger _logger = LoggerFactory.getLogger(AssembleTool.class);

    public static String assembleUrl(String url, Map<String, String> params) {
        if (!url.endsWith("?")) {
            url += "?";
        }
        for (String key : params.keySet()) {
            try {
                if (params.get(key) == null || params.get(key).length() == 0) {
                    url += key + "=" + params.get(key) + "&";
                } else {
                    url += key + "=" + URLEncoder.encode(params.get(key), "utf-8") + "&";
                }
            } catch (UnsupportedEncodingException e) {
                _logger.info("编码不支持");
                throw new RuntimeException("编码不支持", e);
            }
        }
        return url;
    }

}
