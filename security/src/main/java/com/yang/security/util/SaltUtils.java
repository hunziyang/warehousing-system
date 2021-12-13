package com.yang.security.util;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

import java.util.Random;

public class SaltUtils {
    /**
     * 获取盐
     *
     * @return
     */
    public static String getSalt() {
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    /**
     * 对密码加密
     *
     * @return
     */
    public static String encodePassword(String password, String salt) {
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, salt.getBytes());
        return mac.digestHex(password);
    }
}
