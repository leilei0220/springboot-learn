package com.leilei.support;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Author: lei-p
 * Date: 2020/4/6 17:47
 * desc：验证码工具类
 */
public class VerCodeGenerateUtil {
    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    /**
     * 生成6位随机数字字母
     * @return 返回6位字符验证码
     */
    public static String generateVerCode() {
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}