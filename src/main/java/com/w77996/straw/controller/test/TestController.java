package com.w77996.straw.controller.test;

import com.w77996.straw.core.annotation.IgnoreToken;
import com.w77996.straw.core.annotation.Limiter;
import com.w77996.straw.core.jwt.JwtHelper;
import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import com.w77996.straw.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @description: 测试
 * @author: straw
 **/
@RestController
@Slf4j
@IgnoreToken
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/jwt")
    public Result jwtTest(@RequestParam String token) {
        return Result.success(JwtHelper.createJWT(token));
    }

    @GetMapping("/exception")
    public Result exceptionTest() {
        throw new GlobalException(ResultCode.ERROR);
    }


    @GetMapping("/limit")
    @Limiter(perSecond = 1.0, timeOut = 500)
    public String testLimiter() {
        return " success";
    }

    @PostMapping("/upload")
    public String testUpload() {

        String filePath = "C:\\Users\\w77996\\Pictures\\Camera Roll\\微信截图_20190524173628.png";

        RestTemplate rest = new RestTemplate();
//        FileSystemResource resource = new FileSystemResource(new File(filePath));
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//        param.add("smfile", resource);
//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
//        ResponseEntity<String> responseEntity = rest.exchange("https://sm.ms/api/upload", HttpMethod.POST, httpEntity, String.class);
//        System.out.println(responseEntity.getBody());
//
//        String string = rest.postForObject("https://sm.ms/api/upload", param, String.class);
//        System.out.println(string);

        FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);

        params.add("smfile", resource);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://sm.ms/api/upload", HttpMethod.POST, httpEntity, String.class);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("User-Agent","Chrome/69.0.3497.81 Safari/537.36");
//            String result = restTemplate.postForObject("https://sm.ms/api/upload", params, String.class);
        return "ok";

    }
}
