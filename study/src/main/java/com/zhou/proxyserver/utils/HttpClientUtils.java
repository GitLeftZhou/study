package com.zhou.proxyserver.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;


/**
 *
 */
public class HttpClientUtils {

    /**
     * 绕过SSL验证
     * @return SSLContext
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, new SecureRandom());
        return sc;
    }

    /**
     * 获取客户端
     * @return CloseableHttpClient
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public static CloseableHttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        //采用绕过验证的方式处理https请求
        SSLContext sslContext = createIgnoreVerifySSL();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//        HttpClients.custom().setConnectionManager(connManager);
        //创建自定义的httpclient对象

        return HttpClients.custom().setConnectionManager(connManager).build();
    }


    /**
     *
     * @param url 链接
     * @param paramString post数据
     * @return String
     */
    public String doPost(String url,String paramString)  {
        String body = "";
        CloseableHttpClient client = null;
        try{
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //指定报文头Content-type、User-Agent
//            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

            // 构建消息实体
            StringEntity requestEntity = new StringEntity(paramString, Charset.forName("UTF-8"));
            requestEntity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);

            client = getHttpClient();
            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);

            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
//            JSONObject jsonObject = JSON.parseObject(body);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        } finally{
            CloseUtil.close(client);
        }
        return body;
    }

//    public static void main(String[] args) {
//        String respons = new HttpClientUtils().doPost("https://bxgs.jk.szpisp.cn/police-policy-api/openapi/gateway.do", "");
//        System.out.println(respons);
//    }

}
