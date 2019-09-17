package wechatPay.component.base;


import http.okHttp.FrameOkHttpClient;
import http.okHttp.FrameOkHttpModel;
import http.HttpConstant;
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
     * 获取接口返回
     *
     * @param payComponentModel
     * @return
     */
    private String getResponse(PayComponentModel payComponentModel) {
        try {
            FrameOkHttpModel frameOkHttpModel = new FrameOkHttpModel();
            frameOkHttpModel.setContentType(HttpConstant.MEDIA_TYPE_XML);
            frameOkHttpModel.setData(payComponentModel.getRequestData());
            frameOkHttpModel.setHttpMethod(HttpConstant.HttpMethod.POST);
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
}
