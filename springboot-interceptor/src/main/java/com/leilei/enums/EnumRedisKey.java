package com.leilei.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/05/08 18:05
 * @desc:  存入redis 键值对 拼接
 */
public enum EnumRedisKey {

    userLoginInfo_token("userLoginInfo_token", "用户登录唯一标识");

    private String key;
    private String val;

    EnumRedisKey(String key, String val) {
        this.key = key;
        this.val = val;
    }

    /**
     * 拼接key
     *
     * @param k
     * @param params
     * @return
     */
    public static String key(String k, Object... params) {
        if (StringUtils.isEmpty(k)) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        result.append(k);
        if (ArrayUtils.isEmpty(params)) {
            return result.toString();
        }
        for (Object par : params) {
            result.append(":").append(par.toString());
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "EnumRedisKey{" +
                "key='" + key + '\'' +
                ", val='" + val + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
