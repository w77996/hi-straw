package com.w77996.straw.entity.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 七牛授权
 * @author: straw
 **/
@Data
@AllArgsConstructor
public class QiNiuAuth {

    /**
     * 域名
     */
    private String domain;
    /**
     * 七牛key
     */
    private String key;

    private String upToken;
}
