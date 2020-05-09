package com.leilei.web;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author lei
 * @date 2020/5/9 11:09
 * @desc 测试加密解密
 */
public class AAA {

  public static void main(String[] args) {
    String content = "{\"loginTime\":1589006774065,\"token\":\"f03616bf38014d95adb4aa04c1c97750\",\"user\":{\"account\":\"leilei\",\"password\":\"123456\"}}";
    //随机生成密钥
    byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    System.out.println(key.length);
    //构建
    SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

  //加密为16进制表示
    String encryptHex = aes.encryptHex(content);
    System.out.println(encryptHex);
    //解密为字符串
    String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    System.out.println(decryptStr);
  }
}
