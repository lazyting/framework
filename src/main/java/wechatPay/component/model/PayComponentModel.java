package wechatPay.component.model;


import wechatPay.component.constant.PayConstant;

public class PayComponentModel {
    private String openId;// 开放ID
    private String userId;// 绑定用户ID
    private String orderId;// 订单号
    private String ipAddress;// 小程序ip
    private String requestData;//远程接口请求数据
    private String remoteUrl;//远程接口请求地址
    private String remoteName;//远程接口名
    private String callbackData;//回写数据
    private PayConstant.PayProcessName payProcessName;//操作流程枚举

    public String getOpenId() {
        return openId;
    }

    public PayConstant.PayProcessName getPayProcessName() {
        return payProcessName;
    }

    public void setPayProcessName(PayConstant.PayProcessName payProcessName) {
        this.payProcessName = payProcessName;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
