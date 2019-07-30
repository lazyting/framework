package http.okHttp;


import exception.ToolException;
import okhttp3.Headers;
import okhttp3.MediaType;
import utils.EmptyUtil;

import java.util.Map;

public class OkHttpManager {
    private final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String requestMethod, String data, String mediaTypeStr, Map<String, String> headersMap) {
        if (EmptyUtil.isEmpty(url)) {
            throw new ToolException("");
        }
        if (EmptyUtil.isEmpty(mediaTypeStr)) {
            mediaTypeStr = "application/json; charset=utf-8";
        }
        return "";

    }

    private static Headers buildHearder() {
        return Headers.of();
    }
}
