package com.rl.myproject.util.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


public class HttpClientUtil {

    /**
     * url json post 数据 请求 paramUrl
     *
     * @param url url请求地址
     * @param header 请求头信息
     * @param json json数据请求
     * @return
     */
    public static String post(String url, Map<String, String> header, String json) {
        HttpPost httpPost = new HttpPost(url);
        if (isValidStr(json)) {
            StringEntity entity = getEntity(json, StandardCharsets.UTF_8.name(), "application/json; charset=utf-8");
            httpPost.setEntity(entity);
        }
        // httpPost.setHeader("Content-Type", "multipart/form-data;boundary=------------------12314");
        addHeader(httpPost, header);
        // 如果设置了链接超时等属性
        return getResult(httpPost);
    }

    public static String post(String url,  String json) {
        HttpPost httpPost = new HttpPost(url);
        if (isValidStr(json)) {
            StringEntity entity = getEntity(json, StandardCharsets.UTF_8.name(), "application/json; charset=utf-8");
            httpPost.setEntity(entity);
        }
        // 如果设置了链接超时等属性
        return getResult(httpPost);
    }

    public static String get(String url, Map<String, String> header) {
        HttpGet httpget = new HttpGet(url);
        addHeader(httpget, header);
        // 如果设置了链接超时等属性
        return getResult(httpget);
    }

    public static File getFile(String url, Map<String, String> header, String savePath) {
        HttpGet httpget = new HttpGet(url);
        addHeader(httpget, header);
        return getResultFile(httpget, savePath);
    }

    public static File getFile(String url,  String savePath) {
        HttpGet httpget = new HttpGet(url);
        return getResultFile(httpget, savePath);
    }

    public static File getFileByPost(String url, Map<String, String> header, String savePath) {
        HttpPost httpPost = new HttpPost(url);
        addHeader(httpPost, header);
        return getResultFile(httpPost, savePath);
    }

    public static File getFileByPost(String url,  String savePath) {
        HttpPost httpPost = new HttpPost(url);
        return getResultFile(httpPost, savePath);
    }

    public static String get(String url, Map<String, String> header, String charsets) {
        HttpGet httpget = new HttpGet(url);
        addHeader(httpget, header);
        // 如果设置了链接超时等属性
        return getResult(httpget, charsets);
    }

    public static String get(String url) {
        HttpGet httpget = new HttpGet(url);
        // 如果设置了链接超时等属性
        return getResult(httpget);
    }

    /**
     * 自定义解析结果的编码
     *
     * @param url
     * @param charsets
     * @return
     */
    public static String get(String url, String charsets) {
        HttpGet httpget = new HttpGet(url);
        // 如果设置了链接超时等属性
        return getResult(httpget, charsets);
    }

    /**
     * url post 数据 请求 paramUrl
     *
     * @param url url请求地址
     * @param header 请求头信息
     * @param params key-value数据请求
     * @return
     */
    public static String post(String url, Map<String, String> header, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        Iterator<String> keyit = keySet.iterator();
        while (keyit.hasNext()) {
            String key = (String) keyit.next();
            formparams.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(uefEntity);
        addHeader(httpPost, header);
        return getResult(httpPost);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param content
     * @param header
     * @param params
     * @return
     */
    public static String post(String url, byte[] content, Map<String, String> header, Map<String, Object> params) {
        if (null != params && params.size() > 0) {
            url = doFormUrlEncode(url, params, null);
        }
        HttpPost httpPost = new HttpPost(url);
        if (content != null && content.length > 0) {
            httpPost.setEntity(new ByteArrayEntity(content));
        }
        addHeader(httpPost, header);
        return getResult(httpPost);
    }

    public static String postUploadFile(String url, File file) {
        return postUploadFile(url, file, null, null);
    }
    public static String postUploadFile(String url, File file, Map<String, String> header) {
        return postUploadFile(url, file, header, null);
    }
    public static String postUploadFile(String url, File file, Map<String, String> header, Map<String, Object> params) {
        if (null != params && params.size() > 0) {
            url = doFormUrlEncode(url, params, null);
        }
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName());// 文件流
        httpPost.setEntity(builder.build());
        addHeader(httpPost, header);
        return getResult(httpPost);
    }

    public static String put(String url, byte[] content, Map<String, String> header, Map<String, Object> params) {
        if (null != params && params.size() > 0) {
            url = doFormUrlEncode(url, params, null);
        }
        HttpPut put = new HttpPut(url);
        if (content != null && content.length > 0) {
            put.setEntity(new ByteArrayEntity(content));
        }
        addHeader(put, header);
        return getResult(put);
    }

    private final static int connReqTimeout = 5000;// 5s
    private final static int connTimeout = 5000;// 5s
    private final static int socketTimeout = 60000;// 60s

    /**
     * 获取请求配置的Client
     *
     * @param httpType
     * @return
     */
    private static void setConfigClient(HttpRequestBase httpType) {
        // setConnectTimeout：设置连接超时时间，单位毫秒。
        // setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
        // setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(connReqTimeout).setConnectTimeout(connTimeout).setSocketTimeout(socketTimeout).build();
        httpType.setConfig(config);
    }

    public static String put(String url, String content) {
        return put(url, null, content);
    }

    public static String put(String url, Map<String, String> header, String content) {
        HttpPut put = new HttpPut(url);
        addHeader(put, header);
        if (isValidStr(content)) {
            StringEntity entity = getEntity(content, StandardCharsets.UTF_8.name(), "application/json; charset=utf-8");
            put.setEntity(entity);
        }
        return getResult(put);
    }

    public static String delete(String url) {
        HttpDelete delete = new HttpDelete(url);
        return getResult(delete);

    }


    /**
     * 获取数据封装，编码模式
     *
     * @param content
     * @param charset
     * @param contentType
     * @return
     */
    private static StringEntity getEntity(String content, String charset, String contentType) {
        StringEntity entity = new StringEntity(content, charset);// 解决中文乱码问题
        entity.setContentEncoding(charset);
        entity.setContentType(contentType);
        return entity;
    }

    /**
     * 添加请求头信息
     *
     * @param httpType
     * @param header
     */
    private static void addHeader(HttpRequestBase httpType, Map<String, String> header) {
        if (null != header && header.size() > 0) {
            Set<String> headerkey = header.keySet();
            Iterator<String> headerkeyit = headerkey.iterator();
            while (headerkeyit.hasNext()) {
                String key = (String) headerkeyit.next();
                httpType.setHeader(key, header.get(key).toString());
            }
        }
    }

    /**
     * 默认的httpClient 请求配置
     *
     * @param httpType
     * @return
     */
    public static String getResult(HttpRequestBase httpType) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return getResult(httpClient, httpType, StandardCharsets.UTF_8.name());
    }

    public static String getResult(HttpRequestBase httpType, String charsets) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return getResult(httpClient, httpType, charsets);
    }

    /**
     * 自定义的httpClient 请求配置
     *
     * @param httpType
     * @return
     */
    private static String getResult(CloseableHttpClient httpClient, HttpRequestBase httpType, String charsets) {
        HttpResponse response = null;
        try {
            setConfigClient(httpType);
            response = httpClient.execute(httpType);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                // String retString = InputStreamTOString(is, StandardCharsets.UTF_8.name());
                String retString = InputStreamTOString(is, charsets);
                return retString;

            }
            else {
                throw new HttpException("字节内容上传失败，错误的HTTP Status:" + status);
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (ConnectException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (HttpException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static File getResultFile(HttpRequestBase httpType, String savePath) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return getResultFile(httpClient, httpType, savePath);
    }


    private static File getResultFile(CloseableHttpClient httpClient, HttpRequestBase httpType, String savePath) {
        HttpResponse response = null;
        try {
            setConfigClient(httpType);
            response = httpClient.execute(httpType);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedInputStream bin = new BufferedInputStream(is);
                File file = new File(savePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                int size = 0;
                byte[] buf = new byte[1024];
                while ((size = bin.read(buf)) != -1) {
                    out.write(buf, 0, size);
                }
                bin.close();
                out.close();
                return file;
            }
            else {
                throw new HttpException("字节内容上传失败，错误的HTTP Status:" + status);
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (ConnectException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (HttpException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    final static int BUFFER_SIZE = 4096;

    private static String InputStreamTOString(InputStream in, String encoding) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        try {
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            String result = new String(outStream.toByteArray(), encoding);
            in.close();
            outStream.flush();
            outStream.close();
            return result;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }

    /**
     * 将参数转为get方式请求，放到url后面，并utf8加密
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String doFormUrlEncode(String url, Map<String, Object> params, String charset) {
        if (!isValidStr(charset)) {
            charset = StandardCharsets.UTF_8.name();
        }
        StringBuffer buf = new StringBuffer();
        int i = 0;
        Set<String> keySet = params.keySet();
        Iterator<String> keyit = keySet.iterator();
        String value = null;
        while (keyit.hasNext()) {
            String key = (String) keyit.next();
            value = null;
            if (params.get(key) != null) {
                value = params.get(key).toString();
            }
            // URLCodec codec = new URLCodec();
            if (i > 0) {
                buf.append("&");
            }
            try {
                // buf.append(codec.encode(key, charset));
                buf.append(key);
                buf.append("=");
                if (value != null) {
                    // buf.append(codec.encode(value, charset));
                    buf.append(value);
                }
                i++;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (url.contains("?")) {
            return url + buf.toString();
        }
        else {
            return url + "?" + buf.toString();
        }
    }

    public static boolean exists(String url) {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidStr(String str) {
        if (null == str || str.length() == 0 || "".equals(str) || "null".equals(str)) {
            return false;
        }
        return true;
    }
}
