CREATE database crm;

use crm;

/* 用户表 */
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` char(32) NOT NULL COMMENT 'uuid',
  `loginAct` varchar(255) UNIQUE COMMENT '用户的账号名',
  `name` varchar(255) DEFAULT NULL COMMENT '用户真实名字',
  `loginPwd` varchar(255) DEFAULT NULL COMMENT '不能明文存储密码，采用MD5加密',
  `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
  `expireTime` char(19) DEFAULT NULL COMMENT '失效时间为空表示永不失效',
  `lockState` char(1) DEFAULT NULL COMMENT '锁定状态为空表示启用，0表示锁定，1表示启用',
  `deptno` char(4) DEFAULT NULL COMMENT '部门编号',
  `allowIps` varchar(255) DEFAULT NULL COMMENT '允许访问的IP为空时表示IP地址永不受限',
  `createTime` char(19) DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `editTime` char(19) DEFAULT NULL COMMENT '修改时间',
  `editBy` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*Data for the table `tbl_user` */
insert into
  `tbl_user`(
    `id`,
    `loginAct`,
    `name`,
    `loginPwd`,
    `email`,
    `expireTime`,
    `lockState`,
    `deptno`,
    `allowIps`,
    `createTime`,
    `createBy`,
    `editTime`,
    `editBy`
  )
values
  (
    '40f6cdea0bd34aceb77492a1656d9fb2',
    'ls',
    '李四',
    '920deda5c9df2f9c7f81b1f3ae47db8d',
    /* 666666 */
    'ls@163.com',
    '2018-11-27 21:50:05',
    '1',
    'A001',
    '192.168.1.1',
    '2018-11-22 12:11:40',
    '李四',
    NULL,
    NULL
  ),
  (
    '40f6cdea0bd34aceb77492a1656d9fb3',
    'zs',
    '张三',
    '7e1d774ea263041a26945672cb9b63c2',
    /* 101010 */
    'zs@qq.com',
    '2021-11-27 21:50:05',
    '1',
    'A001',
    '192.168.1.1,192.168.1.2,127.0.0.1',
    '2021-11-22 11:37:34',
    '张三',
    NULL,
    NULL
  ),
  (
    "40f6cdea0bd34aceb77492a1656d9fb4",
    "zhiyiYo",
    "之一Yo",
    "0ebb84d6da26e749277b41782f18097d",
    /* 123456 */
    "1319158137@qq.com",
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
  );

/* 市场活动 */
DROP TABLE IF EXISTS `tbl_activity`;
CREATE TABLE `tbl_activity` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL COMMENT '市场活动所有者，外键，关联 tbl_user',
  `name` varchar(255) DEFAULT NULL COMMENT '市场活动名称',
  `startDate` char(10) DEFAULT NULL COMMENT '开始日期：yyyy-MM-dd',
  `endDate` char(10) DEFAULT NULL COMMENT '结束日期：yyyy-MM-dd',
  `cost` varchar(255) DEFAULT NULL COMMENT '成本',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `createTime` char(19) DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `editTime` char(19) DEFAULT NULL COMMENT '修改时间',
  `editBy` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 市场活动评论 */
DROP TABLE IF EXISTS `tbl_activity_remark`;
CREATE TABLE `tbl_activity_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `createTime` char(19) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `activityId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 线索 */
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


/* 线索评论 */
DROP TABLE IF EXISTS `tbl_clue_remark`;
CREATE TABLE `tbl_clue_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `clueId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 线索市场活动关系 */
DROP TABLE IF EXISTS `tbl_clue_activity_relation`;
CREATE TABLE `tbl_clue_activity_relation` (
  `id` char(32) NOT NULL,
  `clueId` char(32) DEFAULT NULL,
  `activityId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 联系人 */
DROP TABLE IF EXISTS `tbl_contacts`;
CREATE TABLE `tbl_contacts` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `customerId` char(32) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `appellation` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mphone` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `birth` char(10) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `contactSummary` varchar(255) DEFAULT NULL,
  `nextContactTime` char(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 联系人评论 */
DROP TABLE IF EXISTS `tbl_contacts_remark`;
CREATE TABLE `tbl_contacts_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `contactsId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 联系人和市场活动关系 */
DROP TABLE IF EXISTS `tbl_contacts_activity_relation`;
CREATE TABLE `tbl_contacts_activity_relation` (
  `id` char(32) NOT NULL,
  `contactsId` char(32) DEFAULT NULL,
  `activityId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 顾客 */
DROP TABLE IF EXISTS `tbl_customer`;
CREATE TABLE `tbl_customer` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `contactSummary` varchar(255) DEFAULT NULL,
  `nextContactTime` char(10) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 顾客评论 */
DROP TABLE IF EXISTS `tbl_customer_remark`;
CREATE TABLE `tbl_customer_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `customerId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 字典类型 */
DROP TABLE IF EXISTS `tbl_dic_type`;
CREATE TABLE `tbl_dic_type` (
  `code` varchar(255) NOT NULL COMMENT '编码是主键，不能为空，不能含有中文。',
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
insert into
  `tbl_dic_type`(`code`, `name`, `description`)
values
  ('appellation', '称呼', ''),
  ('clueState', '线索状态', ''),
  ('returnPriority', '回访优先级', ''),
  ('returnState', '回访状态', ''),
  ('source', '来源', ''),
  ('stage', '阶段', ''),
  ('transactionType', '交易类型', '');


/* 字典值 */
DROP TABLE IF EXISTS `tbl_dic_value`;

CREATE TABLE `tbl_dic_value` (
  `id` char(32) NOT NULL COMMENT '主键，采用UUID',
  `value` varchar(255) DEFAULT NULL COMMENT '不能为空，并且要求同一个字典类型下字典值不能重复，具有唯一性。',
  `text` varchar(255) DEFAULT NULL COMMENT '可以为空',
  `orderNo` varchar(255) DEFAULT NULL COMMENT '可以为空，但不为空的时候，要求必须是正整数',
  `typeCode` varchar(255) DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `tbl_dic_value`(`id`,`value`,`text`,`orderNo`,`typeCode`) values
('06e3cbdf10a44eca8511dddfc6896c55','虚假线索','虚假线索','4','clueState'),
('0fe33840c6d84bf78df55d49b169a894','销售邮件','销售邮件','8','source'),
('12302fd42bd349c1bb768b19600e6b20','交易会','交易会','11','source'),
('1615f0bb3e604552a86cde9a2ad45bea','最高','最高','2','returnPriority'),
('176039d2a90e4b1a81c5ab8707268636','教授','教授','5','appellation'),
('1e0bd307e6ee425599327447f8387285','将来联系','将来联系','2','clueState'),
('2173663b40b949ce928db92607b5fe57','丢失线索','丢失线索','5','clueState'),
('2876690b7e744333b7f1867102f91153','未启动','未启动','1','returnState'),
('29805c804dd94974b568cfc9017b2e4c','成交','成交','7','stage'),
('310e6a49bd8a4962b3f95a1d92eb76f4','试图联系','试图联系','1','clueState'),
('31539e7ed8c848fc913e1c2c93d76fd1','博士','博士','4','appellation'),
('37ef211719134b009e10b7108194cf46','资质审查','资质审查','1','stage'),
('391807b5324d4f16bd58c882750ee632','丢失的线索','丢失的线索','8','stage'),
('3a39605d67da48f2a3ef52e19d243953','聊天','聊天','14','source'),
('474ab93e2e114816abf3ffc596b19131','低','低','3','returnPriority'),
('48512bfed26145d4a38d3616e2d2cf79','广告','广告','1','source'),
('4d03a42898684135809d380597ed3268','合作伙伴研讨会','合作伙伴研讨会','9','source'),
('59795c49896947e1ab61b7312bd0597c','先生','先生','1','appellation'),
('5c6e9e10ca414bd499c07b886f86202a','高','高','1','returnPriority'),
('67165c27076e4c8599f42de57850e39c','夫人','夫人','2','appellation'),
('68a1b1e814d5497a999b8f1298ace62b','因竞争丢失关闭','因竞争丢失关闭','9','stage'),
('6b86f215e69f4dbd8a2daa22efccf0cf','web调研','web调研','13','source'),
('72f13af8f5d34134b5b3f42c5d477510','合作伙伴','合作伙伴','6','source'),
('7c07db3146794c60bf975749952176df','未联系','未联系','6','clueState'),
('86c56aca9eef49058145ec20d5466c17','内部研讨会','内部研讨会','10','source'),
('9095bda1f9c34f098d5b92fb870eba17','进行中','进行中','3','returnState'),
('954b410341e7433faa468d3c4f7cf0d2','已有业务','已有业务','1','transactionType'),
('966170ead6fa481284b7d21f90364984','已联系','已联系','3','clueState'),
('96b03f65dec748caa3f0b6284b19ef2f','推迟','推迟','2','returnState'),
('97d1128f70294f0aac49e996ced28c8a','新业务','新业务','2','transactionType'),
('9ca96290352c40688de6596596565c12','完成','完成','4','returnState'),
('9e6d6e15232549af853e22e703f3e015','需要条件','需要条件','7','clueState'),
('9ff57750fac04f15b10ce1bbb5bb8bab','需求分析','需求分析','2','stage'),
('a70dc4b4523040c696f4421462be8b2f','等待某人','等待某人','5','returnState'),
('a83e75ced129421dbf11fab1f05cf8b4','推销电话','推销电话','2','source'),
('ab8472aab5de4ae9b388b2f1409441c1','常规','常规','5','returnPriority'),
('ab8c2a3dc05f4e3dbc7a0405f721b040','提案/报价','提案/报价','5','stage'),
('b924d911426f4bc5ae3876038bc7e0ad','web下载','web下载','12','source'),
('c13ad8f9e2f74d5aa84697bb243be3bb','价值建议','价值建议','3','stage'),
('c83c0be184bc40708fd7b361b6f36345','最低','最低','4','returnPriority'),
('db867ea866bc44678ac20c8a4a8bfefb','员工介绍','员工介绍','3','source'),
('e44be1d99158476e8e44778ed36f4355','确定决策者','确定决策者','4','stage'),
('e5f383d2622b4fc0959f4fe131dafc80','女士','女士','3','appellation'),
('e81577d9458f4e4192a44650a3a3692b','谈判/复审','谈判/复审','6','stage'),
('fb65d7fdb9c6483db02713e6bc05dd19','在线商场','在线商场','5','source'),
('fd677cc3b5d047d994e16f6ece4d3d45','公开媒介','公开媒介','7','source'),
('ff802a03ccea4ded8731427055681d48','外部介绍','外部介绍','4','source');


/* 交易 */
DROP TABLE IF EXISTS `tbl_transaction`;
CREATE TABLE `tbl_transaction` (
  `id` char(32) NOT NULL,
  `owner` char(32) DEFAULT NULL,
  `money` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `expectedDate` char(10) DEFAULT NULL,
  `customerId` char(32) DEFAULT NULL,
  `stage` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `activityId` char(32) DEFAULT NULL,
  `contactsId` char(32) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `contactSummary` varchar(255) DEFAULT NULL,
  `nextContactTime` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 交易历史 */
DROP TABLE IF EXISTS `tbl_transaction_history`;
CREATE TABLE `tbl_transaction_history` (
  `id` char(32) NOT NULL,
  `stage` varchar(255) DEFAULT NULL,
  `money` varchar(255) DEFAULT NULL,
  `expectedDate` char(10) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `tranId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 交易评论 */
DROP TABLE IF EXISTS `tbl_transaction_remark`;
CREATE TABLE `tbl_transaction_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `tranId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;