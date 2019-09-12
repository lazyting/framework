package wechatPay.component.base;


import http.okHttp.FrameOkHttpClient;
import http.okHttp.FrameOkHttpModel;
import http.okHttp.OkHttpConstant;
import utils.*;
import wechatPay.component.constant.PayConstant;
import wechatPay.component.model.PayComponentModel;
import wechatPay.component.model.PayLogModel;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 1，请求接口，拿到返回
 * 2，返回处理和验证
 */
public abstract class PayComponent implements PayComponentInt {
    private static final String CONTENT_TYPE = "application/xml; charset=utf-8";

    private PayComponentModel payComponentModel;

    public PayComponent(PayComponentModel payComponentModel) {
        this.payComponentModel = payComponentModel;
    }


    /**
     * 记录操作数据
     *
     * @param response
     * @param exceptionLog
     * @param exceptionMsg
     */
    @Override
    public void saveOperatorLog(String response, String exceptionLog, String exceptionMsg) {
        PayLogModel payLogModel = new PayLogModel();
        payLogModel.setOpenId(this.payComponentModel.getOpenId());
        payLogModel.setUserId(this.payComponentModel.getUserId());
        payLogModel.setIpAddress(this.payComponentModel.getIpAddress());
        payLogModel.setOrderId(this.payComponentModel.getOrderId());
        payLogModel.setOperationName(this.payComponentModel.getPayProcessName().getName());
        payLogModel.setResData(response);
        payLogModel.setExceptionLog(exceptionLog);
        payLogModel.setExceptionMsg(exceptionMsg);
        payLogModel.setRecStatus(1);
        payLogModel.setReqData(this.payComponentModel.getRequestData());
        payLogModel.setCreatedDate(new Date());
        payLogModel.setCreatedBy(this.payComponentModel.getUserId());
        //数据库保存paymodel
    }

    @Override
    public Object getResult(Map<String, Object> data, Map<String, Object> extData) {
        return null;
    }

    /**
     * 调用远程接口，验证
     *
     * @return
     */
    public Map<String, Object> doRemote() {
        checkNecessaryData(this.payComponentModel);
        checkRemoteData(this.payComponentModel);
        String response = getResponse(this.payComponentModel);
        String exceptionLog = "";
        String exceptionMsg = "";
        try {
            Map<String, Object> responseMap = XMLParseUtil.xml2Map(response);
            if (PayConstant.SUCCESS.equalsIgnoreCase(responseMap.get(PayConstant.RETURN_CODE).toString())) {
                if (SignManager.verifySign(responseMap)) {
                    return responseMap;
                } else {
                    exceptionMsg = "签名验证失败";
                    throw new RuntimeException(exceptionMsg);
                }
            } else {
                exceptionMsg = String.valueOf(responseMap.get(PayConstant.RETURN_MSG));
                throw new RuntimeException(exceptionMsg);
            }
        } catch (IOException e) {
            exceptionLog = ExceptionUtil.buildExceptionStackTrace(e);
            exceptionMsg = this.payComponentModel.getRemoteName() + "接口交互失败";
            throw new RuntimeException(exceptionMsg, e);
        } finally {
            saveOperatorLog(response, exceptionLog, exceptionMsg);
        }
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
     * 获取接口返回
     *
     * @param payComponentModel
     * @return
     */
    private String getResponse(PayComponentModel payComponentModel) {
        try {
            FrameOkHttpModel frameOkHttpModel = new FrameOkHttpModel();
            frameOkHttpModel.setContentType(OkHttpConstant.MEDIA_TYPE_XML);
            frameOkHttpModel.setData(payComponentModel.getRequestData());
            frameOkHttpModel.setHttpMethod(OkHttpConstant.HttpMethod.POST);
            frameOkHttpModel.setUrl(payComponentModel.getRemoteUrl());
            String response = FrameOkHttpClient.getResponse(frameOkHttpModel);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(payComponentModel.getRemoteName() + "接口调用异常", e);
        }
    }

    /**
     * 检查必要数据是否齐全
     *
     * @param payComponentModel
     */
    private void checkNecessaryData(PayComponentModel payComponentModel) {
        if (StringUtils.isEmpty(payComponentModel.getUserId())) {
            throw new RuntimeException("userId is null");
        }
        if (StringUtils.isEmpty(payComponentModel.getIpAddress())) {
            throw new RuntimeException("ipAddress is null");
        }
        if (StringUtils.isEmpty(payComponentModel.getOpenId())) {
            throw new RuntimeException("openId is null");
        }
        if (StringUtils.isEmpty(payComponentModel.getOrderId())) {
            throw new RuntimeException("orderId is null");
        }
        if (EmptyUtil.isEmpty(payComponentModel.getPayProcessName())) {
            throw new RuntimeException("payProcessName is null");
        }
    }

    /**
     * 检查远程数据是否齐全
     *
     * @param payComponentModel
     */
    private void checkRemoteData(PayComponentModel payComponentModel) {
        if (StringUtils.isEmpty(payComponentModel.getRemoteName())) {
            throw new RuntimeException("remoteName is null");
        }
        if (StringUtils.isEmpty(payComponentModel.getRequestData())) {
            throw new RuntimeException("requestData is null");
        }
        if (StringUtils.isEmpty(payComponentModel.getRemoteUrl())) {
            throw new RuntimeException("remoteUrl is null");
        }
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
