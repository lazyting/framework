package http.okHttp;


import http.okHttp.OkHttpManager;

public class OkHttpUtil {

    public static String CONTENT_TYPE = "text/plain; charset=utf-8";

    public static String httpPost(String url, String data) {
        return OkHttpManager.post(url, "post", data, CONTENT_TYPE, null);
    }

    public static String httpGet(String url, String data) {
        return OkHttpManager.post(url, "get", data, CONTENT_TYPE, null);
    }
}
