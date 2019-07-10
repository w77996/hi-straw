package com.w77996.straw.mapper;

import com.w77996.straw.entity.FileEntity;
import com.w77996.straw.entity.UserFileInfoEntity;
import com.w77996.straw.entity.dto.file.PageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: user和文件关联
 * @author: straw
 **/
@Mapper
public interface UserFileInfoMapper {
    /**
     * 通过userId获取用户文件相关信息
     *
     * @param userId
     * @return
     */
    UserFileInfoEntity getUserFileInfoByUserId(Integer userId);

    /**
     * 更新用户文件信息
     *
     * @param userFileInfoEntity
     * @return
     */
    Integer updateUserFileInfo(UserFileInfoEntity userFileInfoEntity);

    /**
     * 新建用文件信息
     *
     * @param newUserFileInfoEntity
     * @return
     */
    Integer createNewUserFileInfo(UserFileInfoEntity newUserFileInfoEntity);

}
