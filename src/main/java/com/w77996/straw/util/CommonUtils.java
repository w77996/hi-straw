package com.w77996.straw.util;

import java.util.Random;
import java.util.UUID;

/**
 * @description: 常用工具类
 * @author: straw
 **/
public class CommonUtils {

    /**
     * 生成serialId
     *
     * @param length
     * @return
     */
    public static String generateRandomNum(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('0' + random.nextInt(10)));
        }
        return sb.toString();
    }
}
