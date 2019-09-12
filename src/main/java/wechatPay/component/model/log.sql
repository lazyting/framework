CREATE TABLE `sic_t_pay_log` (
  `ID` varchar(45) NOT NULL COMMENT '主键',
  `open_id` varchar(45) DEFAULT NULL COMMENT '开放ID',
  `user_id` varchar(45) DEFAULT NULL COMMENT '绑定用户ID',
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单号',
  `IP_ADDRESS` varchar(45) DEFAULT NULL COMMENT 'IP地址',
  `operation_name` varchar(10) DEFAULT NULL COMMENT '操作流程：创建，取消，支付，预支付等',
  `exception_log` text COMMENT '异常日志',
  `exception_msg` text COMMENT '异常信息',
  `REQ_DATA` text COMMENT '请求数据',
  `RES_DATA` text COMMENT '响应数据',
  `REC_STATUS` int(4) DEFAULT NULL COMMENT '数据状态',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFIED_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  `CREATED_BY` varchar(45) DEFAULT NULL COMMENT '创建者',
  `MODIFIED_BY` varchar(45) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`ID`),
  KEY `idx_pay_log_order_id` (`order_id`)
) COMMENT='预支付信息表';