package http.okHttp;

import http.HttpConstant;

import java.util.Map;

public class FrameOkHttpModel {
    private String url;
    private String data;
    private HttpConstant.HttpMethod httpMethod = HttpConstant.HttpMethod.GET;
    private Map<String, String> headerMap;
    private Map<String, String> formData;
    private String contentType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HttpConstant.HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpConstant.HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
