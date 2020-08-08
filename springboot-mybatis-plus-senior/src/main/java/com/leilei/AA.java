package com.leilei;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/8 15:29
 * @desc
 */
public class AA {
    public static void main(String[] args) {
        String randomKey = AES.generateRandomKey();
        System.out.println(randomKey);
        String root = AES.encrypt("root", randomKey);
        System.out.println(root);
    }
}
