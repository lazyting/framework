package utils;

import java.util.Iterator;
import java.util.Map;

public class MapUtil {
    public static void forkey(Map<String, Object> map) {
        for (String key : map.keySet()) {
            System.out.println("key=" + key + ",value=" + map.get(key));
        }
    }

    public static void forEntry(Map<String, Object> map) {
        for (Map.Entry<String,Object> entry:map.entrySet()) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }
    public static void forValue(Map<String, Object> map) {
        for (Object value:map.values()) {
            System.out.println("value=" + value);
        }
    }

    public static void iteratorMap(Map<String, Object> map) {
        Iterator<Map.Entry<String,Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> entry = iterator.next();
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
        Iterator<String> iterator1 = map.keySet().iterator();
        while (iterator1.hasNext()){
            String key = iterator1.next();
            System.out.println("key=" + key + ",value=" + map.get(key));
        }
    }


}
