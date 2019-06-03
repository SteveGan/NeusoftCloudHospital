package com.neuedu.hospitalbackend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * SHAUtils 加密算法，用于注册加密密码传入数据库与登录验证
 *
 * @author raven
 */
public class SHAUtils {

    public static String encodeData(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] byteData = md.digest();

            //将字节转换为十六进制格式方法一
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}