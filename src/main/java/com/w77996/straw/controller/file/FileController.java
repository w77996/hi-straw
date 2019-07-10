package com.w77996.straw.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.w77996.straw.controller.BaseController;
import com.w77996.straw.core.annotation.IgnoreToken;
import com.w77996.straw.core.constant.Constant;
import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import com.w77996.straw.entity.FileEntity;
import com.w77996.straw.entity.UserFileInfoEntity;
import com.w77996.straw.exception.GlobalException;
import com.w77996.straw.service.IFileService;
import com.w77996.straw.entity.dto.file.*;
import com.w77996.straw.util.QiNiuUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description: 文件服务器
 * @author: straw
 **/
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController extends BaseController {

    @Autowired
    private IFileService fileService;

    @Autowired
    private QiNiuUtil qiNiuUtil;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 七牛云文件域名
     */
    @Value("${qiNiu.domain}")
    private String qiNiuDomain;

    /**
     * 文件限制数量
     */
    private static final Integer LIMIT_FILE_COUNT = 50;

    /**
     * 图片后缀
     */
    String[] imagType = {"jpg", "jpeg", "png", "bmp", "gif"};

    /**
     * 用户文件列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result userFileList(PageDto pageDto) {
        String userId = getUserIdByToken();
        List<FileEntity> fileList = fileService.getFileListByUserId(userId, pageDto);
        //拼接域名
        fileList.forEach(fileEntity -> {
            fileEntity.setFilePath(qiNiuDomain + fileEntity.getFilePath());
        });

        return Result.success(fileList);
    }

    /**
     * 用户文件列表
     *
     * @return
     */
    @GetMapping("/search")
    public Result searchUserFileList(PageDto pageDto) {
        String userId = getUserIdByToken();
        List<FileEntity> fileList = fileService.searchFileListByName(userId, pageDto);
        //拼接域名
        fileList.forEach(fileEntity -> {
            fileEntity.setFilePath(qiNiuDomain + fileEntity.getFilePath());
        });

        return Result.success(fileList);
    }

    /**
     * 删除文件
     *
     * @return
     */
    @DeleteMapping("/qiniu/{id}")
    public Result delUserFile(@PathVariable("id") String fileId) {
        String userId = getUserIdByToken();
        FileEntity fileEntity = fileService.getFileByUserIdAndFileId(userId, fileId);
        if (ObjectUtils.isEmpty(fileEntity)) {
            return Result.error(ResultCode.ERROR_RESOURCE_NO_FOUND);
        }
        fileService.deleteFileById(fileId);
        //删除七牛云文件
        qiNiuUtil.delete(fileEntity.getFilePath());
        return Result.success();
    }

    /**
     * 删除文件
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delSmsUserFile(@PathVariable("id") String fileId) {
        String userId = getUserIdByToken();
        FileEntity fileEntity = fileService.getFileByUserIdAndFileId(userId, fileId);
        if (ObjectUtils.isEmpty(fileEntity)) {
            return Result.error(ResultCode.ERROR_RESOURCE_NO_FOUND);
        }
        fileService.deleteFileById(fileId);
        return Result.success();
    }

    /**
     * 文件上传类
     *
     * @param multipartFile
     * @param fileUploadDto
     * @return
     */
//    @IgnoreToken
    @PostMapping("/upload")
    public Result uploadUserFile(@RequestParam("file") MultipartFile multipartFile, FileUploadDto fileUploadDto) {
        log.info("enter upload");
        String userId = getUserIdByToken();

        //校验用户是否存在
        UserFileInfoEntity userFileInfoEntity = fileService.getUserFileInfoByUserId(userId);
        if (userFileInfoEntity == null) {
            throw new GlobalException(ResultCode.ERROR_RESOURCE_NO_FOUND);
        }
        //用户是否是vip
        boolean isVip = userFileInfoEntity.getIsVip() == 1 ? true : false;
        //获取文件名，文件后缀名
        String fileName = multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        long fileSizeKb = 0;
        //非vip受限
        if (!isVip) {
            //文件上传数量受限
            if (userFileInfoEntity.getFileNum() > LIMIT_FILE_COUNT) {
                throw new GlobalException(ResultCode.COUNT_LIMIT);
            }
            //上传文件类型受限
            boolean isImageFile = Arrays.asList(imagType).contains(prefix.toLowerCase());
            if (!isImageFile) {
                throw new GlobalException(ResultCode.FILE_TYPE_ERROR);
            }
            //上传文件大小受限
            fileSizeKb = multipartFile.getSize();
            if (fileSizeKb > Constant.LIMIT_FILE_SIZE) {
                throw new GlobalException(ResultCode.SIZE_LIMIT);
            }
        }

        log.info("fileName :{} ,prefix :{},fileSizeKb :{}", fileName, prefix, fileSizeKb);
        //生成七牛云token
        QiNiuAuth qiNiuAuth = qiNiuUtil.generateToken(userId, fileName);
        //是否已上传过这个文件
        if (fileService.countFileExistByUserId(userId, fileName) != 0) {
            return Result.error(ResultCode.ERROR_RESOURCE_EXIST);
        }
        //七牛云上传图片
        String filePath = qiNiuUtil.uploadImage(multipartFile, qiNiuAuth.getKey(), qiNiuAuth.getUpToken());
        //文件表实体类
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserId(Integer.parseInt(userId));
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        fileEntity.setFileType(prefix);
        fileEntity.setFileSize(fileSizeKb);
        //用户与文件信息实体类
        userFileInfoEntity.setFileNum(userFileInfoEntity.getFileNum() + 1);
        userFileInfoEntity.setFileSize(userFileInfoEntity.getFileSize() + fileSizeKb);
        userFileInfoEntity.setLeftSize(userFileInfoEntity.getTotalSize() - userFileInfoEntity.getFileSize());
        log.info(userFileInfoEntity.toString());
        Integer res = fileService.uploadFile(fileEntity, userFileInfoEntity);
        return Result.success(qiNiuDomain + filePath);
    }

    /**
     * 获取QiNiuToken
     *
     * @return
     */
    @GetMapping("/qiNiu/token")
    public Result getQiNiuToken(String fileType) {
        return Result.success();
    }

}
