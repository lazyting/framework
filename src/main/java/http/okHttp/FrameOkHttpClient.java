package http.okHttp;

import okhttp3.*;
import okhttp3.Request.Builder;
import utils.EmptyUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FrameOkHttpClient {
    // HTTP
    private static OkHttpClient httpClient;

    // HTTPS
    private static OkHttpClient httpsClient;

    /**
     * 使用https
     */
    static {
        getHttpClient();
        getHttpsClient();
    }

    public static String getResponse(FrameOkHttpModel model) {
        Request request = buildRequest(model);
        OkHttpClient hc = "s".equals(String.valueOf(model.getUrl().charAt(4))) ? httpsClient : httpClient;
        return responseStr(request, hc);
    }

    private static void getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(OkHttpConstant.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(OkHttpConstant.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(OkHttpConstant.WRITE_TIME_OUT, TimeUnit.MILLISECONDS);
        httpClient = builder.build();
    }

    private static void getHttpsClient() {
        try {
            TrustManager[] trustAllCerts = buildTrustManagers();
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            builder.connectTimeout(OkHttpConstant.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(OkHttpConstant.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(OkHttpConstant.WRITE_TIME_OUT, TimeUnit.MILLISECONDS);
            httpsClient = builder.build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    public static Request buildRequest(FrameOkHttpModel model) {
        return buildRequest(model.getUrl(), model.getData(), model.getHttpMethod(), model.getHeaderMap(), model.getContentType(), model.getFormData());
    }

    public static Request buildRequest(String url, String data, OkHttpConstant.HttpMethod httpMethod, Map<String, String> headerMap, String contentType, Map<String, String> formData) {
        Builder builder = new Request.Builder().url(url);
        if (EmptyUtil.isNotEmpty(headerMap)) {
            builder.headers(Headers.of(headerMap));
        }
        switch (httpMethod) {
            case POST:
                if (EmptyUtil.isNotEmpty(data))
                    builder.post(buildRequestBody(contentType, data));
                break;
            case DELETE:
                if (EmptyUtil.isNotEmpty(data))
                    builder.delete(buildRequestBody(contentType, data));
                break;
            case PUT:
                if (EmptyUtil.isNotEmpty(data))
                    builder.put(buildRequestBody(contentType, data));
                break;
            case FORM_DATA:
                if (EmptyUtil.isNotEmpty(formData))
                    builder.post(builFormDataRequestBody(formData));
                break;
            case POST_FORM:
                if (EmptyUtil.isNotEmpty(formData))
                    builder.post(builFormDataRequestBody(formData));
                else
                    builder.post(builFormPostRequestBody(data, contentType));
                break;
            case GET:
            default:
                break;

        }
        return builder.build();
    }

    /**
     * 创建请求体
     *
     * @param contentTpye
     * @param data
     * @return
     */
    public static RequestBody buildRequestBody(String contentTpye, String data) {
        return RequestBody.create(MediaType.parse(contentTpye), data);
    }

    /**
     * 创建文件请求体
     *
     * @param contentTpye
     * @param file
     * @return
     */
    public static RequestBody builFileRequestBody(String contentTpye, File file) {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(contentTpye), file))
                .build();
    }

    /**
     * 以form-data或者form-post（提交表单:键值对形式）格式创建请求体
     *
     * @param formData
     * @return
     */
    public static RequestBody builFormDataRequestBody(Map<String, String> formData) {
        if (EmptyUtil.isEmpty(formData)) {
            return null;
        }
        FormBody.Builder body = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = formData.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            body.add(entry.getKey(), entry.getValue());
        }
        return body.build();
    }

    /**
     * 以form-post（提交表单：指定媒体类型）格式创建请求体
     *
     * @param data
     * @return
     */
    public static RequestBody builFormPostRequestBody(String data, String contentType) {
        if (EmptyUtil.isEmpty(data)) {
            return null;
        }
        return FormBody.create(MediaType.parse(contentType), data);

    }


    public static String responseStr(Request request, OkHttpClient hc) {

        Response response = null;
        try {
            response = hc.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            throw new RuntimeException("response error!!", e);
        }
    }
}
