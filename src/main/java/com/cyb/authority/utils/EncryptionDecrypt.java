package com.cyb.authority.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密/解密
 */
public class EncryptionDecrypt {

    public static String encryptionMD5(String str) {
        Object obj = new SimpleHash("MD5", str, null, 1024);
        return obj.toString();
    }
}
