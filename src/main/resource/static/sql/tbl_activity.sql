use crm;
DROP TABLE IF EXISTS `tbl_activity`;
CREATE TABLE `tbl_activity` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL COMMENT '市场活动所有者，外键，关联 tbl_user',
  `name` varchar(255) DEFAULT NULL COMMENT '市场活动名称',
  `startDate` char(10) DEFAULT NULL COMMENT '开始日期：yyyy-MM-dd',
  `endDate` char(10) DEFAULT NULL  COMMENT '结束日期：yyyy-MM-dd',
  `cost` varchar(255) DEFAULT NULL COMMENT '成本',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `createTime` char(19) DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `editTime` char(19) DEFAULT NULL COMMENT '修改时间',
  `editBy` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;