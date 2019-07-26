package com.zhou.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static byte[] stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        return secretBytes;
    }

    public final static String byte2String(byte[] secretBytes) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            // 把密文转换成十六进制的字符串形式
            int j = secretBytes.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = secretBytes[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static String byte2String2(byte[] secretBytes) {
        try {
            // 把密文转换成十六进制的字符串形式
            int j = secretBytes.length;
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < j; i++) {
                byte byte0 = secretBytes[i];
                sb.append((Integer.toHexString(byte0>>>4 & 0x0f)));
                sb.append((Integer.toHexString(byte0 & 0x0f)));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String id = "123456";
       // String txt = "{\“billNo\”:\”000000000001\”}";
        byte [] bb = MD5Util.stringToMD5(id);
        System.out.println(bb);
        System.out.println(MD5Util.byte2String(bb));
        System.out.println(MD5Util.byte2String2(bb));

//        char c = '\u0000';
//        String str1 = String.valueOf(c);
//        String str2 = String.valueOf((char)0x0);
//        Integer sb;
//        System.out.println(str1);
//        System.out.println(str2);
//        System.out.println(str1.equals(str2));

    }
}
