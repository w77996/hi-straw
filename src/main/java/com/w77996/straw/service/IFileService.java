package com.w77996.straw.service;

import com.w77996.straw.entity.FileEntity;
import com.w77996.straw.entity.UserFileInfoEntity;
import com.w77996.straw.entity.dto.file.PageDto;
import com.w77996.straw.mapper.FileMapper;
import com.w77996.straw.mapper.UserFileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 文件业务类
 * @author: straw
 **/
@Service
public class IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserFileInfoMapper userFileInfoMapper;

    /**
     * 通过usreId获取文件分页
     *
     * @param userId
     * @param pageDto
     * @return
     */
    public List<FileEntity> getFileListByUserId(String userId, PageDto pageDto) {
        return fileMapper.getFileListByUserId(Integer.parseInt(userId), pageDto);
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteFileById(String fileId) {
        FileEntity fileEntity = fileMapper.getFileByFileId(fileId);
        if (fileEntity != null) {
            UserFileInfoEntity userFileInfoEntity = userFileInfoMapper.getUserFileInfoByUserId(fileEntity.getUserId());
            if (userFileInfoEntity != null) {
                userFileInfoEntity.setFileNum(userFileInfoEntity.getFileNum() - 1);
                userFileInfoEntity.setFileSize(userFileInfoEntity.getFileSize() - fileEntity.getFileSize() );
                userFileInfoEntity.setLeftSize(fileEntity.getFileSize() + userFileInfoEntity.getLeftSize());
                userFileInfoMapper.updateUserFileInfo(userFileInfoEntity);
            }
        }
        return fileMapper.deleteFileById(Integer.parseInt(fileId));
    }

    /**
     * 上传文件
     *
     * @param fileEntity
     */
    public Integer uploadFile(FileEntity fileEntity) {
        return fileMapper.insertFile(fileEntity);
    }

    /**
     * 上传文件
     *
     * @param fileEntity
     * @param userFileInfoEntity
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer uploadFile(FileEntity fileEntity, UserFileInfoEntity userFileInfoEntity) {
        Integer fileInsertRes = fileMapper.insertFile(fileEntity);
        Integer userFileInfoRes = 0;
        if (fileInsertRes > 0) {
            userFileInfoRes = userFileInfoMapper.updateUserFileInfo(userFileInfoEntity);
        }
        return 1;
    }

    /**
     * 上传数量
     *
     * @param userId
     * @return
     */
    public Integer countFileByUserIdAndFilePath(String userId) {
        return fileMapper.countFileByUserIdAndFilePath(Integer.parseInt(userId));
    }

    /**
     * 查询文件是否上传过
     *
     * @param userId
     * @param fileName
     * @return
     */
    public Integer countFileExistByUserId(String userId, String fileName) {
        return fileMapper.countFileExistByUserId(Integer.parseInt(userId), fileName);
    }

    /**
     * 通过id获取文件
     *
     * @param fileId
     * @return
     */
    public FileEntity getFileByFileId(String fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    /**
     * 通过id查找userFileInfo
     *
     * @param userId
     * @return
     */
    public UserFileInfoEntity getUserFileInfoByUserId(String userId) {
        return userFileInfoMapper.getUserFileInfoByUserId(Integer.parseInt(userId));
    }

    /**
     * 通过文件名查找
     *
     * @param userId
     * @param pageDto
     * @return
     */
    public List<FileEntity> searchFileListByName(String userId, PageDto pageDto) {
        return fileMapper.searchFileListByName(Integer.parseInt(userId), pageDto);
    }

    /**
     * 获取
     *
     * @param fileId
     * @return
     */
    public FileEntity getFileByUserIdAndFileId(String userId, String fileId) {
        return fileMapper.getFileByUserIdAndFileId(Integer.parseInt(userId), Integer.parseInt(fileId));
    }
}
