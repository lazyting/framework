package http;

public class HttpConstant {
    /**
     * 普通格式
     */
    public static final String MEDIA_TYPE_FORM_DATA = "application/from-data"; //form-data参数格式
    public static final String MEDIA_TYPE_XML = "application/xml; charset=utf-8"; //XML参数格式
    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8"; //JSON参数格式
    public static final String MEDIA_TYPE_NORMAL = "application/x-www-form-urlencoded";// 数据是个普通表单

    /**
     * 文件格式
     */
    public static final String FILE_MIXED = "multipart/mixed";
    public static final String FILE_ALTERNATIVE = "multipart/alternative";
    public static final String FILE_DIGEST = "multipart/digest";
    public static final String FILE_PARALLEL = "multipart/parallel";
    public static final String FILE_FORM = "multipart/form-data";

    /**
     * 时间
     */
    public static final Integer CONNECT_TIME_OUT = 60 * 1000;
    public static final Integer READ_TIME_OUT = 5 * 60 * 1000;
    public static final Integer WRITE_TIME_OUT = 5 * 60 * 1000;

    /**
     * User-Agent
     * 模拟浏览器登录
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";


    public enum HttpMethod{
        GET,POST,DELETE,PUT,FORM_DATA,POST_FORM;
    }
}
