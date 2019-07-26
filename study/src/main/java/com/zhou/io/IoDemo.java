package com.zhou.io;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;

/**
 *  InputStream OutputStream
 *     1.API 1) 节点 File
 *              Byte[]
 *         2) 包装 FileInputStream FileOutputStream
 *              ByteArrayInputStream ByteArrayOutputStream
 *         3) 处理流 BufferedInputStream BufferedOutputStream
 *    2.处理过程:
 *        选择流 InputStream in = new BufferedInputStream(
 *                                    new FileInputStream(
 *                                        new File("z.txt")))
 *        定义缓冲区 byte [] flush = new byte[1024]
 *        定义读取长度 int len = 0
 *        循环读取，跳出循环条件 while(-1 != (len = in.read(flush)))
 *        取出数据 byte [] data = Arrays.copyOfRange(flush, 0, len);
 *
 *  Reader   Writer 处理字符，过程同Stream
 *
 *  处理字符集，字节流<-->字符流
 *      InputStreamReader(InputStream in, CharsetDecoder dec )  字节-->字符 解码
 *      OutputStreamWriter(OutputStream out, CharsetEncoder dec )  字符-->字节 编码
 *
 *  DataInputStream 基本数据类型+String  保留数据和类型
 *  ObjectInputStream 对象流 序列化和反序列化
 *  PrintStream 打印流
 *
 *
 */
public class IoDemo {

    //@Test
    public void test(){
        byte [] dest = null;
        /*
         * 文件流->字节流  文件传输
         */
        try (InputStream in = new BufferedInputStream(new FileInputStream(new File("a.txt")));
             ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ) {
            byte[] flush = new byte[1024];
            int len = 0;
            while(-1 != (len = in.read(flush))){
                bos.write(flush,0,len);
            }
            bos.flush();
            dest = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 字节流->文件流  文件传输
         */
        try (InputStream in = new BufferedInputStream(new ByteArrayInputStream(dest));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("b.txt")));
        ) {
            byte[] flush = new byte[1024];
            int len = 0;
            while(-1 != (len = in.read(flush))){
                out.write(flush,0,len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
