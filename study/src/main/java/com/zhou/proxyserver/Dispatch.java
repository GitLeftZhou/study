package com.zhou.proxyserver;

import com.zhou.proxyserver.utils.CloseUtil;
import com.zhou.proxyserver.utils.HttpClientUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Dispatch implements Runnable {

    private Socket client;

    String proxyURL = "https://bxgs.jk.szpisp.cn/police-policy-api/openapi/gateway.do";

    public Dispatch(Socket client) {
        this.client=client;
    }

    @Override
    public void run() {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String msg = null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                while((msg = br.readLine()).length()>0){
                    stringBuilder.append(msg);
                    stringBuilder.append("\r\n");
                }
                String requestInfo = stringBuilder.toString().trim();
//                System.out.println(requestInfo);
                Request request = new Request(requestInfo);
                HttpClientUtils httpClientUtils = new HttpClientUtils();
                String s = httpClientUtils.doPost(proxyURL, "");
                System.out.println("==============================");
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(client);
        }
    }
}
