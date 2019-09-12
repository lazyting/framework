package wechatPay;


import security.Coder;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名类
 */
public class SignUtil {
    private static final String SIGN_TYPE = "MD5";

    /**
     * 使用TreeMap对map的参数名进行ASCII从小到大排序（字典排序）
     *
     * @param map
     * @return
     */
    public static String getSortStringByTreeMap(Map<String, Object> map) {
        map = new TreeMap<>(map);
        StringBuilder resultSB = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            resultSB.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        resultSB.deleteCharAt(resultSB.length() - 1);
        System.out.println(resultSB);
        return resultSB.toString();
    }

    /**
     * 方式二：使用HashMap，并使用Arrays.sort排序
     *
     * @param map
     * @return
     */
    public static String getSortStringByHashMap(Map<String, Object> map) {
        String[] sortedKeys = map.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);// 排序请求参数
        StringBuilder resultSB = new StringBuilder();
        for (String key : sortedKeys) {
            resultSB.append(key).append("=").append(map.get(key)).append("&");
        }
        resultSB.deleteCharAt(resultSB.length() - 1);
        System.out.println(resultSB);
        return resultSB.toString();
    }

    /**
     * 获得签名字符串
     *
     * @param sortData 按照参数名ASCII字典序排序的数据
     * @param key      商户平台设置的密钥key
     * @return md5加密后的数据
     */
    public static String getSign(String sortData, String key) throws Exception {
        String signTemp = sortData + "&key=" + key;
        byte[] md5Bytes = Coder.encryptMD5(signTemp.getBytes());
        return new String(md5Bytes).toUpperCase();
    }

    /**
     * 获得签名字符串
     *
     * @param map 待排序数据
     * @param key 商户平台设置的密钥key
     * @return md5加密后的数据
     * @throws Exception
     */
    public static String getSign(Map<String, Object> map, String key) throws Exception {
        String sortData = getSortStringByHashMap(map);
        return getSign(sortData, key);
    }
}
