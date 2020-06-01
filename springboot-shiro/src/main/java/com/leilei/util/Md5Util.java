package com.leilei.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/1 9:03
 */
public class Md5Util {

    /**
     *
     * @param salt 加密盐值
     * @param source 加密次数
     * @return "md5" 10 注意与 shiroConfig 中的密码凭证器一致
     */
    public static String encrype(String salt,String source){
        SimpleHash simpleHash = new SimpleHash("md5",source,salt,10);
        return simpleHash.toString();
    }

    public static void main(String[] args) {

        System.out.println(encrype("qq","123456"));

    }
}
