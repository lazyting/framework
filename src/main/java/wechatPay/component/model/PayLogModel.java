package wechatPay.component.model;

import java.util.Date;

public class PayLogModel {
    private static final long serialVersionUID = 1L;

    private String ID;// 主键
    private String openId;// 开放ID
    private String userId;// 绑定用户ID
    private String orderId;// 订单号
    private String ipAddress;// 小程序ip
    private String operationName;// 操作名
    private String reqData;// 请求报文
    private String resData;// 返回报文
    private String exceptionLog;// 异常日志
    private String exceptionMsg;// 异常信息
    private Integer recStatus;// 数据状态
    private Date createdDate;// 创建时间
    private Date modifiedDate;// 修改时间
    private String createdBy;// 创建者
    private String modifiedBy;// 修改者

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getExceptionLog() {
        return exceptionLog;
    }

    public void setExceptionLog(String exceptionLog) {
        this.exceptionLog = exceptionLog;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "PayLogModel{" +
                "ID='" + ID + '\'' +
                ", openId='" + openId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", operationName='" + operationName + '\'' +
                ", reqData='" + reqData + '\'' +
                ", resData='" + resData + '\'' +
                ", exceptionLog='" + exceptionLog + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", recStatus=" + recStatus +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
