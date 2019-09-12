package wechatPay.component;


import utils.EmptyUtil;
import wechatPay.component.base.PayComponent;
import wechatPay.component.constant.PayConstant;
import wechatPay.component.model.PayComponentModel;

import java.util.Map;

public class OrderQuery extends PayComponent {

    public OrderQuery(PayComponentModel payComponentModel) {
        super(payComponentModel);
    }


    @Override
    public Object getResult(Map<String, Object> data, Map<String, Object> extData) {
        if (EmptyUtil.isEmpty(data)) {
            throw new RuntimeException("查询订单返回数据为空");
        }
        if (PayConstant.SUCCESS.equalsIgnoreCase(String.valueOf(data.get(PayConstant.RETURN_CODE))) &&
                PayConstant.SUCCESS.equalsIgnoreCase(String.valueOf(data.get(PayConstant.RESULT_CODE)))) {
            if (PayConstant.SUCCESS.equalsIgnoreCase(String.valueOf(data.get(PayConstant.TRADE_STATE)))) {
                return PayConstant.SUCCESS;
            } else {
                //订单状态不为成功，只返回只返回out_trade_no（订单号，必传）和attach（附加数据，选传）
                return PayConstant.FAIL;
            }
        } else {
            throw new RuntimeException("查询订单失败");
        }
    }
}
