package wechatPay.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.EmptyUtil;
import utils.SignManager;
import utils.UuidUtil;
import wechatPay.component.base.PayComponent;
import wechatPay.component.constant.PayConstant;
import wechatPay.component.model.PayComponentModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Prepay extends PayComponent {

    private PayComponentModel payComponentModel;

    public Prepay(PayComponentModel payComponentModel) {
        super(payComponentModel);
        this.payComponentModel = payComponentModel;
    }

    /**
     * 记录业务数据
     *
     * @param prepayId
     * @param totalMoney
     * @param timeStart
     * @param productDes
     */
    private void saveBusLog(String prepayId, BigDecimal totalMoney, Date timeStart, String productDes) {
    }


    @Override
    public Object getResult(Map<String, Object> data, Map<String, Object> extData) {
        if (EmptyUtil.isEmpty(data)) {
            throw new RuntimeException("预下单返回数据为空");
        }
        if (PayConstant.SUCCESS.equalsIgnoreCase(data.get(PayConstant.RESULT_CODE).toString())) {
            String prepayId = String.valueOf(data.get(PayConstant.PREPAY_ID));
            saveBusLog(prepayId, new BigDecimal(String.valueOf(extData.get("totalMoney"))), new Date(), String.valueOf(extData.get("productDes")));
            return assembleDataToApp(prepayId);
        } else {
            throw new RuntimeException("预下单失败");
        }
    }

    /**
     * 组装返回小程序数据
     *
     * @param prepayId
     * @return
     */
    private JSONObject assembleDataToApp(String prepayId) {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", PayConstant.APPID);
        map.put("timeStamp", String.valueOf(new Date().getTime()));
        map.put("nonceStr", UuidUtil.getUUID32len());
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", PayConstant.SignType.MD5.getName());
        map.put("paySign", SignManager.getSign(map));
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

}
