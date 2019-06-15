package com.panghu.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author panghu
 * @title: MD5Util
 * @projectName seckill
 * @date 19-6-10 上午9:09
 */
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一次MD5加密，用于网络传输
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        //避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
        //注意这里要和前端保持一致   加引号和不加引号是有区别的
        String str =""+salt.charAt((0))+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次md5加密，用于数据库存储
     * @param inputPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String inputPass,String salt){
        String str = ""+ salt.charAt((0))+salt.charAt(2)+inputPass+salt.charAt(5);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String DBsalt){
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, DBsalt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.err.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }

}
