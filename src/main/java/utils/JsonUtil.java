package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    /**
     * 字符串转对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     * @throws IOException
     */
    public static T json2Object(String jsonStr, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json转字符串
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static String obj2String(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 有时间转换的json转字符串
     *
     * @param obj
     * @param dateFormate
     * @return
     * @throws IOException
     */
    public static String obj2String(Object obj, String dateFormate) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(dateFormate));
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 通过使用map获得value
     *
     * @param jsonStr
     * @param key
     * @return
     * @throws IOException
     */
    public static Object getValueByMap(String jsonStr, String key) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(jsonStr, HashMap.class);
        return map.get(key);
    }
}
