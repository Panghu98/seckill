package com.panghu.seckill.util;

import java.util.UUID;


/**
 * 生成唯一ID
 * @author panghu
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
