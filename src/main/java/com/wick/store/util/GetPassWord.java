package com.wick.store.util;

import org.springframework.util.DigestUtils;

public class GetPassWord{
    public static String getmd5PassWord;

    public static String getmd5PassWord(String password, String salt) {
        //进行md5加密，循环3次加密
        for (int i=0;i<3;i++){
            password=DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
