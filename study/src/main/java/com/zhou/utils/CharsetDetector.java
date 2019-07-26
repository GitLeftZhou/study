package com.zhou.utils;

import java.io.*;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;


/**
 *
 */
public class CharsetDetector {
    private boolean found = false;
    private String encoding = null;

    /**
     * 传入一个文件(File)对象，检查文件编码
     * 
     * @param file
     *            File对象实例
     * @return 文件编码，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessEncoding(File file) throws FileNotFoundException, IOException {
        return guessEncoding(new FileInputStream(file), new nsDetector(3));
    }

    /**
     * <pre>
     * 获取文件的编码
     * @param file
     *            File对象实例
     * @param languageHint
     *            语言提示区域代码 @see #nsPSMDetector ,取值如下：
     *             1 : Japanese
     *             2 : Chinese
     *             3 : Simplified Chinese
     *             4 : Traditional Chinese
     *             5 : Korean
     *             6 : Dont know(default)
     * </pre>
     * 
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式(不确定的时候，返回可能的字符编码序列)；若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessEncoding(File file, int languageHint) throws FileNotFoundException, IOException {
        return guessEncoding(new FileInputStream(file), new nsDetector(languageHint));
    }

    /**
     * 获取字节数组的编码
     * @param bytes
     * @return
     * @throws IOException
     */
    public String guessEncoding(byte [] bytes) throws IOException {
        return guessEncoding(new ByteArrayInputStream(bytes), new nsDetector(3));
    }

    /**
     * 获取流的编码
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String guessEncoding(InputStream inputStream) throws IOException {
        return guessEncoding(inputStream, new nsDetector(3));
    }

    /**
     * 获取流的编码
     * @param inputStream
     * @param det
     * @return
     * @throws IOException
     */
    private String guessEncoding(InputStream inputStream, nsDetector det) throws IOException {
        // Set an observer...
        // The Notify() will be called when a matching charset is found.
        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                encoding = charset;
                found = true;
            }
        });

        BufferedInputStream imp = new BufferedInputStream(inputStream);
        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = false;

        while ((len = imp.read(buf, 0, buf.length)) != -1) {
            // Check if the stream is only ascii.
            isAscii = det.isAscii(buf, len);
            if (isAscii) {
                break;
            }
            // DoIt if non-ascii and not done yet.
            done = det.DoIt(buf, len, false);
            if (done) {
                break;
            }
        }
        imp.close();
        det.DataEnd();

        if (isAscii) {
            encoding = "ASCII";
            found = true;
        }

        if (!found) {
            String[] prob = det.getProbableCharsets();
            //这里将可能的字符集组合起来返回
            for (int i = 0; i < prob.length; i++) {
                if (i == 0) {
                    encoding = prob[i];
                } else {
                    encoding += "," + prob[i];
                }
            }

            if (prob.length > 0) {
                // 在没有发现情况下,也可以只取第一个可能的编码,这里返回的是一个可能的序列
                return encoding;
            } else {
                return null;
            }
        }
        return encoding;
    }



    /**
     * 猜测字符串的编码，利用String decode 再encode后对比字符串
     * 存在字符集编码重合的可能，并不一定完全有效
     * 支持常见字符集"UTF-8","ISO-8859-1","GB2312","GBK",
     *               "GB18030","Big5","Unicode","ASCII"
     * @param str
     * @return
     */
    public String guessEncoding(String str) {
        String encode[] = new String[]{
                "UTF-8",
                "ISO-8859-1",
                "GB2312",
                "GBK",
                "GB18030",
                "Big5",
                "Unicode",
                "ASCII"
        };
        for (int i = 0; i < encode.length; i++){
            try {
                if (str.equals(new String(str.getBytes(encode[i]), encode[i]))) {
                    return encode[i];
                }
            } catch (Exception ex) {
            }
        }

        return "";
    }
}