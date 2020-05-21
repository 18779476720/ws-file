package example.ws;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Map;

public class EmarkHttpHelper {
    private static final int MAX_TIMEOUT = 3000; // 超时时间
    private static final int MAX_TOTAL = 10; // 最大连接数
    private static final int ROUTE_MAX_TOTAL = 3; // 每个路由基础的连接数
    private static final int MAX_RETRY = 5; // 重试次数
    private static PoolingHttpClientConnectionManager connMgr; // 连接池
    private static HttpRequestRetryHandler retryHandler; // 重试机制

    static {
        cfgPoolMgr();
        cfgRetryHandler();
    }

    public static JSONObject sendHttp(String reqType, String url, Map<String, String> headers, Object param) {
        HttpRequestBase reqBase = getHttpType(reqType, url);
        //LOGGER.info("\n--->>开始向地址[{}]发起 [{}] 请求", url, reqBase.getMethod());
        //LOGGER.info("--->>请求头为{}", JSON.toJSONString(headers));
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = getHttpClient();
        // 设置请求url
        config(reqBase);

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                reqBase.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 添加参数 参数是json字符串
        if (param != null && param instanceof String) {
            String paramStr = String.valueOf(param);
            //LOGGER.info("--->>请求参数为：{}", paramStr);
            ((HttpEntityEnclosingRequest) reqBase).setEntity(new StringEntity(String.valueOf(paramStr), ContentType.create("application/json", "UTF-8")));
        } else if (param != null && param instanceof byte[]) {// 参数时字节流数组
            //LOGGER.info("--->>请求参数为文件流");
            byte[] paramBytes = (byte[]) param;
            ((HttpEntityEnclosingRequest) reqBase).setEntity(new ByteArrayEntity(paramBytes));
        }

        // 响应对象
        CloseableHttpResponse res = null;
        // 响应内容
        String resCtx = null;
        try {
            // 执行请求
            res = httpClient.execute(reqBase);
            //LOGGER.info("--->>执行请求完毕，响应状态：{}", res.getStatusLine());
            if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                //LOGGER.info("--->>HTTP访问异常:{}", res.getStatusLine().getStatusCode());
            }
            // 获取请求响应对象和响应entity
            HttpEntity httpEntity = res.getEntity();
            if (httpEntity != null) {
                resCtx = EntityUtils.toString(httpEntity, "utf-8");
                //LOGGER.info("--->>获取响应内容：{}", resCtx);
            }

        } catch (Exception e) {
            e.printStackTrace();
//            throw new DefineException("请求失败", e);
        } finally {
            IOUtils.closeQuietly(res);
        }
        //LOGGER.info("--->>请求执行完毕，耗费时长：{} 秒", (System.currentTimeMillis() - startTime) / 1000);
        JSONObject json = JSONObject.parseObject(resCtx);
        return json;
    }

    private static HttpRequestBase getHttpType(String reqType, String url) {
        if ("PUT".equals(reqType)) {
            return new HttpPut(url);
        } else if ("GET".equals(reqType)) {
            return new HttpGet(url);
        } else if ("POST".equals(reqType)) {
            return new HttpPost(url);
        } else if ("DELETE".equals(reqType)) {
            return new HttpDelete(url);
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient() {
        // HttpHost proxy = new HttpHost("192.168.7.22",8888);
        return HttpClients.custom()
                // .setProxy(proxy)
                .setConnectionManager(connMgr).setRetryHandler(retryHandler).build();
    }

    private static void cfgPoolMgr() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https", sslsf).build();

        // 连接池管理器
        connMgr = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        connMgr.setMaxTotal(MAX_TOTAL);
        // 每个路由基础的连接数
        connMgr.setDefaultMaxPerRoute(ROUTE_MAX_TOTAL);
    }

    private static void cfgRetryHandler() {
        retryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException e, int excCount, HttpContext ctx) {
                // 超过最大重试次数，就放弃
                if (excCount > MAX_RETRY) {
                    return false;
                }
                // 服务器丢掉了链接，就重试
                if (e instanceof NoHttpResponseException) {
                    return true;
                }
                // 不重试SSL握手异常
                if (e instanceof SSLHandshakeException) {
                    return false;
                }
                // 中断
                if (e instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (e instanceof UnknownHostException) {
                    return false;
                }
                // 连接超时
                if (e instanceof ConnectTimeoutException) {
                    return false;
                }
                // SSL异常
                if (e instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientCtx = HttpClientContext.adapt(ctx);
                HttpRequest req = clientCtx.getRequest();
                // 如果是幂等请求，就再次尝试
                if (!(req instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }

                return false;
            }
        };
    }

    private static void config(HttpRequestBase httpReqBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(MAX_TIMEOUT).setConnectTimeout(MAX_TIMEOUT).setSocketTimeout(MAX_TIMEOUT).build();
        httpReqBase.setConfig(requestConfig);
    }
}
