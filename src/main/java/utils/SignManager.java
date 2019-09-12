package utils;

import security.Coder;
import wechatPay.component.constant.PayConstant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SignManager {
    /**
     * 使用HashMap，并使用Arrays.sort排序
     *
     * @param map
     * @return
     */
    public static String getSortString(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            //返回数据中的sign不参与加密
            if (entry.getKey().equalsIgnoreCase("sign")) {
                continue;
            }
            if (EmptyUtil.isNotEmpty(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        return result;
    }

    /**
     * 获得签名字符串
     *
     * @param sortData 已按照参数名ASCII字典序排序的数据
     * @return md5加密后的数据
     */
    public static String getSign(String sortData) {
        String signTemp = sortData + "key=" + PayConstant.MCHIDKEY;
        String md5Value = Coder.encryptMD5(signTemp).toUpperCase();
        return md5Value;
    }

    /**
     * 获得签名字符串
     *
     * @param map 待排序数据
     * @return md5加密后的数据
     * @throws Exception
     */
    public static String getSign(Map<String, Object> map) {
        String sortData = getSortString(map);
        return getSign(sortData);
    }

    /**
     * 验证签名
     *
     * @param data
     * @return
     */
    public static boolean verifySign(Map<String, Object> data) {
        String sign = data.get(PayConstant.SIGN).toString();
        String calcSign = getSign(data);
        return calcSign.equalsIgnoreCase(sign);
    }
}
