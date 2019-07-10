package com.w77996.straw.mapper;

import com.w77996.straw.entity.FileEntity;
import com.w77996.straw.entity.dto.file.PageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 文件数据库操作
 * @author: straw
 **/
@Mapper
public interface FileMapper {

    /**
     * 通过用户获取文件列表
     *
     * @param userId
     * @param pageDto
     * @return
     */
    List<FileEntity> getFileListByUserId(@Param("userId") Integer userId, @Param("page") PageDto pageDto);

    /**
     * 删除文件
     *
     * @param i
     * @return
     */
    Integer deleteFileById(Integer i);

    /**
     * 插入文件记录
     *
     * @param fileEntity
     * @return
     */
    Integer insertFile(FileEntity fileEntity);

    /**
     * 文件数量
     *
     * @param userId
     * @return
     */
    Integer countFileByUserIdAndFilePath(@Param("userId") Integer userId);

    /**
     * 查询用户是否上传过文件
     *
     * @param userId
     * @param fileName
     * @return
     */
    Integer countFileExistByUserId(@Param("userId") Integer userId, @Param("fileName") String fileName);

    /**
     * 通过id查找file
     *
     * @param fileId
     * @return
     */
    FileEntity getFileByFileId(@Param("fileId") String fileId);

    /**
     * 通过文件名查找文件列表
     *
     * @param userId
     * @param pageDto
     * @return
     */
    List<FileEntity> searchFileListByName(@Param("userId") Integer userId, @Param("page") PageDto pageDto);

    /**
     * 根据userId和fileId查找文件
     *
     * @param userId
     * @param fileId
     * @return
     */
    FileEntity getFileByUserIdAndFileId(@Param("userId")Integer userId, @Param("id")Integer fileId);
}
