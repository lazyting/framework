package wechatPay.component;

import utils.EmptyUtil;
import utils.ExceptionUtil;
import utils.SignManager;
import utils.StringUtils;
import wechatPay.component.base.PayComponent;
import wechatPay.component.constant.PayConstant;
import wechatPay.component.model.PayComponentModel;
import wechatPay.component.model.PayLogModel;

import java.util.Date;
import java.util.Map;

public class Callback extends PayComponent {
    private PayComponentModel payComponentModel;

    public Callback(PayComponentModel payComponentModel) {
        super(payComponentModel);
        this.payComponentModel = payComponentModel;
    }

    /**
     * 微信回写
     *
     * @param callbakcMap
     * @return
     */
    public Object callback(Map<String, Object> callbakcMap) {
        checkCallbackData(this.payComponentModel);
        String exceptionMsg = PayConstant.CallbackReturn.SUCCESS.getMessage();
        String exceptionLog = "";
        String openId = "";
        String orderId = "";
        boolean callbackFlag = false;
        try {
            //判断返回是否有数据
            if (PayConstant.SUCCESS.equalsIgnoreCase(String.valueOf(callbakcMap.get(PayConstant.RESULT_CODE)))) {
                if (SignManager.verifySign(callbakcMap)) {
                    if (PayConstant.SUCCESS.equalsIgnoreCase(String.valueOf(callbakcMap.get(PayConstant.RESULT_CODE)))) {
                        //回调成功
                        openId = String.valueOf(callbakcMap.get("openid"));
                        orderId = String.valueOf(callbakcMap.get("out_trade_no"));
                        callbackFlag = true;
                        return callbakcMap;
                    } else {
                        exceptionMsg = StringUtils.isEmpty(String.valueOf(callbakcMap.get(PayConstant.ERR_CODE_DES))) ? String.valueOf(callbakcMap.get(PayConstant.ERR_CODE_DES)) : "交易失败";
                    }
                } else {
                    exceptionMsg = "签名校验失败";
                }
            } else {
                //此处需要考虑只返回两个字段的数据怎么存 return_code=fail
                exceptionMsg = String.valueOf(callbakcMap.get("return_msg"));
            }
        } catch (Exception e) {
            exceptionLog = ExceptionUtil.buildExceptionStackTrace(e);
            exceptionMsg = "回调接口交互失败";
        } finally {
            if (!callbackFlag) {
                exceptionMsg = PayConstant.CallbackReturn.FAIL.getMessage().replace("-----need replace-----", exceptionMsg);
            }
            saveCallbackOperatorLog(openId, orderId, exceptionMsg, exceptionLog, exceptionMsg);
        }
        return exceptionMsg;
    }

    /**
     * 记录回调日志
     *
     * @param openid       只能从回调数据内取
     * @param orderId      只能从回调数据内取
     * @param response
     * @param exceptionLog
     * @param exceptionMsg
     */
    private void saveCallbackOperatorLog(String openid, String orderId, String response, String exceptionLog, String exceptionMsg) {
        PayLogModel payLogModel = new PayLogModel();
        payLogModel.setOpenId(openid);
        payLogModel.setUserId("wechat");
        payLogModel.setIpAddress(this.payComponentModel.getIpAddress());
        payLogModel.setOrderId(orderId);
        payLogModel.setOperationName(this.payComponentModel.getPayProcessName().getName());
        payLogModel.setResData(response);
        payLogModel.setExceptionLog(exceptionLog);
        payLogModel.setExceptionMsg(exceptionMsg);
        payLogModel.setRecStatus(1);
        payLogModel.setReqData(this.payComponentModel.getCallbackData());
        payLogModel.setCreatedDate(new Date());
        payLogModel.setCreatedBy("wechat");
        //数据库保存paymodel
    }

    /**
     * 检查回写数据是否齐全
     *
     * @param payComponentModel
     */
    private void checkCallbackData(PayComponentModel payComponentModel) {
        if (StringUtils.isEmpty(payComponentModel.getCallbackData())) {
            throw new RuntimeException("callbackData is null");
        }
        if (EmptyUtil.isEmpty(payComponentModel.getPayProcessName())) {
            throw new RuntimeException("payProcessName is null");
        }
    }
}
