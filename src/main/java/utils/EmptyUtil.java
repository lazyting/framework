package utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Map;

public class EmptyUtil {
    public static boolean isEmpty(String... strings) {
        return !isNotEmpty(strings);
    }

    public static boolean isNotEmpty(String... values) {
        boolean flag = true;
        if (values == null) {
            flag = false;
        }
        for (int i = 0, size = values.length; i < size; i++) {
            if (isEmpty(values[i])) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static boolean isEmpty(Object[] object) {
        return isNull(object) || object.length < 1;
    }

    public static boolean isEmpty(String text) {
        return isNull(text) || text.length() < 1;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(Object[] object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isEmpty(JSONObject json) {
        return isNull(json) || json.size() < 1;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.size() < 1;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.size() < 1;
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof Collection) {
            return isEmpty((Collection<?>) object);
        } else if (object instanceof Map) {
            return isEmpty((Map<?, ?>) object);
        }
        return isNull(object) || "".equals(object);
    }

    public static boolean isNull(Object object) {
        if (object == null) return true;
        if (object instanceof String && "".equals(((String) object).trim())) return true;
        return false;
    }
}
