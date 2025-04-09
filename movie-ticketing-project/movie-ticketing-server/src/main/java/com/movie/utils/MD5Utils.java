package com.movie.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

@Component
public class MD5Utils {
    public String md5Encrypt(String input,String salt){
        if(input == null){
            return null;
        }
       return DigestUtil.md5Hex(input+salt);
    }
}
