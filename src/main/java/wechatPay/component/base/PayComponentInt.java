package wechatPay.component.base;

import java.util.Map;

public interface PayComponentInt {
    /**
     * 记录轨迹日志
     *
     * @param response
     * @param exceptionLog
     * @param exceptionMsg
     */
    void saveOperatorLog(String response, String exceptionLog, String exceptionMsg);

    /**
     * 获取结果
     *
     * @param data
     * @param extData
     * @return
     */
    Object getResult(Map<String, Object> data, Map<String, Object> extData);
}
