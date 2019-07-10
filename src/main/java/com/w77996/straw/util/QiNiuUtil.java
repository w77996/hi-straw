package com.w77996.straw.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.w77996.straw.core.result.ResultCode;
import com.w77996.straw.entity.dto.file.QiNiuAuth;
import com.w77996.straw.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @description: 七牛云工具类
 * @author: straw
 **/
@Component
@Slf4j
public class QiNiuUtil {

    /**
     * 七牛accessKey
     */
    @Value("${qiNiu.accessKey}")
    private String qiNiuAccessKey;
    /**
     * 七牛密钥
     */
    @Value("${qiNiu.secretKey}")
    private String qiNiuSecretKey;

    /**
     * 七牛bucket
     */
    @Value("${qiNiu.bucket}")
    private String qiNiuBucket;




    /**
     * 上传图片
     *
     * @param file
     * @param key
     * @param token
     * @return
     */
    public String uploadImage(MultipartFile file, String key, String token) {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        String filePath = null;
        //生成上传凭证，不指定key的情况下，以文件内容的hash值作为文件名
        Response response = null;
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(qiNiuAccessKey, qiNiuSecretKey);
            String upToken = auth.uploadToken(qiNiuBucket);
            try {
                response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                log.info("上传结果 {} {}", putRet.hash, putRet.key);
                filePath = putRet.key;
            } catch (QiniuException ex) {
                try {
                    response = ex.response;
                    log.error(response.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            //ignore
            ex.printStackTrace();
        }
        return filePath;
    }


    /**
     * 七牛云生成token
     *
     * @param fileName
     * @return
     */
    public QiNiuAuth generateToken(String userId, String fileName) {
        Auth auth = Auth.create(qiNiuAccessKey, qiNiuSecretKey);
        String key = "upload/file/000/" + userId + "/" + fileName;
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(qiNiuBucket, key, expireSeconds, putPolicy);
        return new QiNiuAuth("https://www.w77996.cn", key, upToken);
    }

    /**
     * 删除图片
     *
     * @param key
     */
    public void delete(String key) {
        Configuration cfg = new Configuration(Zone.zone2());
        Auth auth = Auth.create(qiNiuAccessKey, qiNiuSecretKey);
        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //调用delete方法移动文件
            bucketManager.delete(qiNiuBucket, key);
        } catch (QiniuException e) {
            //捕获异常信息
            throw new GlobalException(ResultCode.ERROR);
        }
    }
}
