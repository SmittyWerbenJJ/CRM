use crm;
DROP TABLE IF EXISTS `tbl_clue`;
CREATE TABLE `tbl_clue` (
  `id` char(32) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL COMMENT '名称',
  `appellation` varchar(255) DEFAULT NULL COMMENT '称呼',
  `owner` char(32) DEFAULT NULL COMMENT '所有者',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `job` varchar(255) DEFAULT NULL COMMENT '职位',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '公司座机',
  `website` varchar(255) DEFAULT NULL COMMENT '公司网站',
  `mphone` varchar(255) DEFAULT NULL COMMENT '手机',
  `state` varchar(255) DEFAULT NULL COMMENT '线索状态',
  `source` varchar(255) DEFAULT NULL COMMENT '线索来源',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建者',
  `createTime` char(19) DEFAULT NULL COMMENT '创建时间',
  `editBy` varchar(255) DEFAULT NULL COMMENT '编辑者',
  `editTime` char(19) DEFAULT NULL COMMENT '编辑时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `contactSummary` varchar(255) DEFAULT NULL COMMENT '联系纪要',
  `nextContactTime` char(10) DEFAULT NULL COMMENT '下次联系时间',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

