package com.zhou.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*
 * AES对称加密和解密
 */
public class AESEncoder {


    /**
     *
     * @param keys  密钥数组
     * @param content  待加密数据
     * @return
     */
    public static String AESEncode(byte [] keys,String content){
        try {
            /*//1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();*/

            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(keys, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
          //10.将加密后的数据转换为字符串
            String AES_encode=new String(Base64.getEncoder().encodeToString(byte_AES));
          //11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;         
    }

    public static String AESDecode(byte [] keys,String content){
        try {
            /*//1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();*/
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(keys, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content = Base64.getDecoder().decode(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            return new String(byte_decode, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        //如果有错就返加nulll
        return null;         
    }

    public static byte[] stringToMD5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        return secretBytes;
    }

    public static void main(String[] args) {
        String id = "123456";
        byte [] md5bytes = AESEncoder.stringToMD5(id);
        for (int a: md5bytes ) {
            System.out.print(a);
            System.out.print(",");
        }
        String content = "{\"billNo\":\"000000000001\"}";
        String encodeR = AESEncoder.AESEncode(md5bytes, content);
        System.out.println(encodeR);
        String decodeR = AESEncoder.AESDecode(md5bytes, encodeR);
        System.out.println(decodeR);

    }

}