package http.fromData;

import com.squareup.okhttp.*;
import com.squareup.okhttp.Request.Builder;
import exception.ToolException;
import http.HttpMethod;
import utils.EmptyUtil;


import java.io.IOException;
import java.util.Map;

/**
 * 使用form-data调用接口
 */
public class HttpUtil {

    private static final String MEDIA_TYPE = "application/from-data"; //form-data参数格式

    /**
     * 使用form-data请求接口的另一种形式（简写版）
     * 只适用于post方式
     *
     * @param builder
     * @param url
     * @param data    请求参数
     * @return
     */
    private String postHttp(FormEncodingBuilder builder, String url, String data,HttpMethod method) {
        OkHttpClient httpClient = new OkHttpClient();
        Request request;
        if (method.equals(HttpMethod.POST)){
            request = new Builder().url(url).post(builder.build()).post(RequestBody.create(MediaType.parse(MEDIA_TYPE), data)).build();
        }else{
            request = buildRequest(url,data,null,MEDIA_TYPE,method);
        }
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 请求接口
     *
     * @param url
     * @param data
     * @param headersMap
     * @param mediaTypeStr
     * @param method
     * @return
     */
    public static String httpResponseString(String url, String data, Map<String, String> headersMap, String mediaTypeStr, HttpMethod method) {
        if (EmptyUtil.isEmpty(url)) {
            throw new ToolException("URL could not be null");
        }

        Request request = buildRequest(url, data, headersMap, mediaTypeStr, method);

        //判断URL是http请求还是https请求
//        OkHttpClient hc = "s".equals(String.valueOf(url.charAt(4))) ? httpsClient : httpClient;
        OkHttpClient hc = new OkHttpClient();
        return responseStr(request, hc);
    }

    /**
     * 创建请求信息
     *
     * @param url
     * @param data
     * @param headersMap
     * @param mediaTypeStr
     * @param method
     * @return
     */
    public static Request buildRequest(String url, String data, Map<String, String> headersMap, String mediaTypeStr, HttpMethod method) {
        Builder builder = new Request.Builder().url(url);

        if (EmptyUtil.isNotEmpty(headersMap)) {
            builder.headers(buildHeaders(headersMap));
        }

        if (EmptyUtil.isEmpty(method)) {
            method = HttpMethod.GET;
        }
        if (EmptyUtil.isEmpty(mediaTypeStr)) {
            mediaTypeStr = "application/json; charset=utf-8";
        }
        switch (method) {
            case POST:
                if (EmptyUtil.isNotEmpty(data)) {
                    builder.post(buildRequestBody(mediaTypeStr, data));
                }
                break;
            case FORM_POST:
                if (EmptyUtil.isNotEmpty(data)) {
                    builder.post(buildFormBody(data));
                }
                break;
            case PUT:
                if (EmptyUtil.isNotEmpty(data)) {
                    builder.put(buildRequestBody(mediaTypeStr, data));
                }
                break;
            case DELETE:
                if (EmptyUtil.isNotEmpty(data)) {
                    builder.delete(buildRequestBody(mediaTypeStr, data));
                }
                break;
            case GET:
            default:
                break;
        }
        return builder.build();
    }

    /**
     * 根据指定参数媒体类型转化请求
     *
     * @param mediaTypeStr
     * @param data
     * @return
     */
    public static RequestBody buildRequestBody(String mediaTypeStr, String data) {
        return RequestBody.create(MediaType.parse(mediaTypeStr), data);
    }

    public static RequestBody buildFormBody(String data) {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        formEncodingBuilder.add("mq", data);
        String currentSeconds = String.valueOf(System.currentTimeMillis() / 1000);
        String encryptCurrentSeconds = null;
        try {
//            encryptCurrentSeconds = AESCoder.encrypt(currentSeconds);//加密
            encryptCurrentSeconds = currentSeconds;//未加密
        } catch (Exception e) {
//            logger.error("encrpty by aes error!!", e);
        }
        formEncodingBuilder.add("sign", encryptCurrentSeconds);
        RequestBody formBody = formEncodingBuilder.build();
        return formBody;
    }

    public static Headers buildHeaders(Map<String, String> headerMap) {
        return Headers.of(headerMap);
    }

    /**
     * 组织返回参数
     *
     * @param request
     * @param hc
     * @return
     */
    public static String responseStr(Request request, OkHttpClient hc) {
        Response response = null;
        try {
            response = hc.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
            throw new ToolException("response error!!", e);
        }
    }

    /**
     * 组织返回参数
     *
     * @param request
     * @param hc
     * @return
     */
    public static Response responseStr2(Request request, OkHttpClient hc) {
        Response response = null;
        try {
            response = hc.newCall(request).execute();
            return response;
        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
            throw new ToolException("response error!!", e);
        }
    }
}
