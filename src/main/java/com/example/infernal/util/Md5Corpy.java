package com.example.infernal.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/5
 * \* Time: 14:07
 * \* Description: md5加密
 * \
 */
public class Md5Corpy {

    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,5);
    }


    //MD5加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
