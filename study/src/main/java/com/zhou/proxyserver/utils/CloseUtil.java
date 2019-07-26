package com.zhou.proxyserver.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {

    public static <T extends Closeable> void close(T...t) {
        try {
            for (T t1 : t) {
                if (t1 != null){
                    t1.close();
                }
            }
        } catch (IOException e) {
        }
    }

}
