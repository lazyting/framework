package wechatPay;


import com.alibaba.fastjson.JSON;
import com.sun.jmx.snmp.Timestamp;
import utils.UuidUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 组装数据类
 */
public class AssembleDataUtil {
    private static final String SIGN_TYPE = "MD5";

    /**
     * 组装返回小程序数据
     * @param prepayId
     * @param paySign
     * @return
     */
    public static String assembleDataToApp(String prepayId, String paySign) {
        Map<String, String> map = new HashMap<>();
        map.put("timeStamp", String.valueOf(new Timestamp(System.currentTimeMillis())));
        map.put("nonceStr", UuidUtil.getUUID32len());
        map.put("package", "prepayId=" + prepayId);
        map.put("signType", "MD5");
        map.put("paySign", paySign);
        return JSON.toJSONString(map);
    }
}
