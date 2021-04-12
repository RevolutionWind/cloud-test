package com.decent.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * http请求工具类
 *
 * @author 王元鑫
 * @date 2015/7/22
 */
@Slf4j
public final class HttpClient {
    private static final List<String> FAIL_RESPONSES = new ArrayList<String>() {
        private static final long serialVersionUID = 6278013186698590142L;

        {
            add("请求异常");
            add("读取超时");
        }
    };

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param headers         请求头
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @param content  内容
     * @return http返回值
     */
    public static String post(String url, List<NameValuePair> headers, String content, String charset, int connectTimeout, int socketTimeout) {
        String returnStr;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            for (NameValuePair pair : headers) {
                httpPost.addHeader(pair.getName(), pair.getValue());
            }
            StringEntity entity = new StringEntity(content, charset);
            httpPost.setEntity(entity);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);
            httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            StringBuilder stringBuffer = new StringBuilder(100);
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            returnStr = stringBuffer.toString();
            reader.close();
            return returnStr;
        } catch (SocketTimeoutException e) {
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            return "";
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, content, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL GET连接
     *
     * @param url            get访问URL地址
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String get(String url, int connectTimeout, int socketTimeout) {
        String returnStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpGet.setConfig(config);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnStr = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
                }
            }
            httpGet.abort();
            return returnStr;
        } catch (SocketTimeoutException e) {
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            return "";
        } catch (Exception e) {
            log.error("地址: {}请求异常: {}", url, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL GET连接(带请求头的)
     *
     * @param url            get访问URL地址
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String bankGet(String url, String header, int connectTimeout, int socketTimeout) {
        String returnStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization", header);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpGet.setConfig(config);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnStr = EntityUtils.toString(entity);
                }
            }
            httpGet.abort();
            return returnStr;
        } catch (SocketTimeoutException e) {
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            return "";
        } catch (Exception e) {
            log.error("地址: {}请求异常: {}", url, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param params         POST内容
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String post(String url, List<NameValuePair> params, String charset, int connectTimeout, int socketTimeout) {
        String returnStr;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);
            httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            StringBuilder stringBuffer = new StringBuilder(100);
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            returnStr = stringBuffer.toString();
            reader.close();
            return returnStr;
        } catch (SocketTimeoutException e) {
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            return "";
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, params, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param params         POST内容String字符串
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String postString(String url, String params, String charset, int connectTimeout, int socketTimeout) {
        String returnStr;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            StringEntity entity = new StringEntity(params, charset);
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);
            httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            StringBuilder stringBuffer = new StringBuilder(100);
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            returnStr = stringBuffer.toString();
            reader.close();
            return returnStr;
        } catch (SocketTimeoutException e) {
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            return "";
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, params, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /**
     * 判断是否请求失败
     *
     * @param result 响应
     * @return true:请求失败;false:请求通过
     */
    public static boolean requestFail(String result) {
        // 连接超时
        if (StringUtils.isBlank(result)) {
            return true;
        }
        // 读取超时||请求异常
        return FAIL_RESPONSES.contains(result);
    }

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param params         POST内容
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static byte[] postStream(String url, List<NameValuePair> params, String charset, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);
            httpResponse = httpClient.execute(httpPost);
            return EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (SocketTimeoutException e) {
            log.warn("地址: {}|参数: {} 读取超时", url, params);
            return null;
        } catch (ConnectTimeoutException e) {
            log.warn("地址: {}|参数: {} 连接超时", url, params);
            return null;
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, params, e);
            return null;
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }


}
