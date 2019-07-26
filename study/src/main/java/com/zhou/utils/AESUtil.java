package com.zhou.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.IntStream;


public class AESUtil {
    private static int digit = 128;

    private static byte[] getMD5(String plainText){
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return secretBytes;
    }

    public static String aesEncrypt(String content, String password) throws Exception {
        String result = "";
        // KeyGenerator利用SecureRandom生成的伪随机序列产生密钥
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        //播种：使用密码的字节数组做为种子
        random.setSeed(password.getBytes("UTF-8"));
        kgen.init(digit, random);
        SecretKey secretKey = kgen.generateKey();
        //得到密钥
        byte[] enCodeFormat = secretKey.getEncoded();

        //也可以使用MD5 摘要算法获取密钥  甚至使用原始的字符串字节数组做为密钥
        // byte[] enCodeFormat = getMD5(password);
        System.out.println("enCodeFormat"+Arrays.toString(enCodeFormat));
        // 包装Key
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("UTF-8");
        cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
        byte[] resultByte = cipher.doFinal(byteContent);
        result = parseByte2HexStr(resultByte);
        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.println(key.getFormat());
        System.out.println("加密类加密结果:"+result);
        return result;
    }

    public static String aesDecrypt(String content, String password) throws Exception {
        byte[] contentByte = parseHexStr2Byte(content);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes("UTF-8"));
        kgen.init(digit, random);

        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
//        byte[] enCodeFormat = getMD5(password);

        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        System.out.println("加密类解密KEY开始！~！！！！！！！:");
        System.out.println(Arrays.toString(key.getEncoded()));
//        System.out.println(key.getAlgorithm().getBytes());
        System.out.println(key.getFormat());
        System.out.println("加密类解密KEY结束！~！！！！！！！:");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key); // 初始化

        byte[] result = cipher.doFinal(contentByte);

        String returStr = new String(result,"UTF-8");
        return returStr;

    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            String key = "6aff762dbbf31e40ac507256bb4c4ecf";
            String encryptResultStr = aesEncrypt("！@#￥%…如果今天升级不成功，客户要请我吃饭…&*（）~~~老天保佑，永无bugPDZA2018！@#￥%……&*（）6101D020000126", key);
            System.out.println( encryptResultStr);
//			// 解密
//            String encryptResultStr =  "E2D5133F0EAD5CEE1FB500AB956013DEE6CAAC0A7B71AE07C0C237B34BCF14B7A4FF360D6148C51B2FE7AB971208AF98254FDA23ABCA53E8AD198D15502DAF7D98943E8CE3F29D75DBFD8363427875061AA54F49736621AA2E33384E124EDEF87546C0B798A5332C5B5426FAE6232F7F101BA69295C3C48FBCB9268EE6D93F9300F0E5F3E3ED7864FE4C4AAD10AE93C9CD020F92E012559BAB90903AF0CFCF3D";
            String decryptResult = aesDecrypt(encryptResultStr, key);
            System.out.println(decryptResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }


}