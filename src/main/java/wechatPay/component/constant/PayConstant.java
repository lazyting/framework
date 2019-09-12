package wechatPay.component.constant;

public class PayConstant {


    /**
     * 小程序号
     */
    public static final String APPID = "";
    /**
     * 商户号
     */
    public static final String MCHID = "";
    /**
     * 商户平台设置的密钥key
     */
    public static final String MCHIDKEY = "";
    /**
     * 支付成功回调地址
     */
    public static final String NOTIFY_URL = "";

    /**
     * 支付字段
     */
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    /**
     * 返回状态码
     */
    public static final String RETURN_CODE = "return_code";
    /**
     * 返回的状态说明
     */
    public static final String RETURN_MSG = "return_msg";
    /**
     * 业务状态码
     */
    public static final String RESULT_CODE = "result_code";
    /**
     * 预支付交易会话标识
     */
    public static final String PREPAY_ID = "prepay_id";
    /**
     * 交易状态
     */
    public static final String TRADE_STATE = "trade_state";
    /**
     * 回调返回的错误代码
     */
    public static final String ERR_CODE = "err_code";
    /**
     * 回调返回的错误信息
     */
    public static final String ERR_CODE_DES = "err_code_des";
    /**
     * 签名
     */
    public static final String SIGN = "sign";

    /**
     * 操作流程枚举
     */
    public enum PayProcessName {
        UNIFORM_ORDER(0, "调用统一下单"), PAY_CALLBACK(1, "支付回调"), PAY_CANCLE(2, "支付取消"), PAY_DONE(3, "支付结束"), CHECK_ORDER(4, "查询订单");
        private Integer code;
        private String name;

        PayProcessName(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 支付模式枚举
     */
    public enum PayMode {
        SMALL_APP("1", "小程序");
        private String name;
        private String code;

        PayMode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 交易类型枚举
     */
    public enum TradeType {
        JSAPI(0, "JSAPI", "小程序支付"), NATIVE(1, "NATIVE", "Native支付"), APP(2, "APP", "app支付"), MWEB(3, "MWEB", "H5支付");

        private Integer code;
        private String value;
        private String name;

        TradeType(Integer code, String value, String name) {
            this.code = code;
            this.value = value;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 签名类型枚举
     */
    public enum SignType {
        MD5(0, "MD5"), HMAC_SHA256(1, "HMAC-SHA256");

        private Integer code;
        private String name;

        SignType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 订单查询接口返回的订单状态
     */
    public enum OrderStatusCheck {
        SUCCESS("", "支付成功"), REFUND("", "转入退款"), NOTPAY("", "未支付"), CLOSED("", "已关闭"), REVOKED("", "已撤销（刷卡支付）"), USERPAYING("", "用户支付中"), PAYERROR("", "支付失败");

        private String flag;
        private String value;

        OrderStatusCheck(String flag, String value) {
            this.flag = flag;
            this.value = value;
        }

        public String getFlag() {
            return flag;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 回调成功失败枚举
     */
    public enum CallbackReturn {
        SUCCESS("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>", "成功"),
        FAIL("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[-----need replace-----]]></return_msg></xml>", "失败");
        private String message;
        private String explain;

        CallbackReturn(String message, String explain) {
            this.message = message;
            this.explain = explain;
        }

        public String getMessage() {
            return message;
        }
    }
}
