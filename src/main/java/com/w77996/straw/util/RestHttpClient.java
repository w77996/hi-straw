package com.w77996.straw.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

/**
 * @description: restTemplate请求微信
 * @author: straw
 **/
public class RestHttpClient {

    /**
     * http 请求工具类
     *
     * @param url
     * @param method
     * @param params
     * @return
     */
    public static JSONObject client(String url, HttpMethod method, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        String json = null;
        try {
            // 微信编码返回特么是ISO-8859-1 的
            json  = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(json);
    }

    public static void main(String[] args) {
        JSONObject js = client("http://localhost:8888/v1/test/test?timestamp=1&sign=jn", HttpMethod.GET, null);
        System.out.println(js.getString("123"));
    }
}
