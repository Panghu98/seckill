package com.panghu.seckill.util;


import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author panghu
 */
public class ValidatorUtil {

    /**
     *     默认以1开头后面加10个数字为手机号
     */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(src);
        return m.matches();
    }
}
