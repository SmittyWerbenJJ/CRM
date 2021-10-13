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
/*Data for the table `tbl_activity` */
insert into
  `tbl_activity`(
    `id`,
    `owner`,
    `name`,
    `startDate`,
    `endDate`,
    `cost`,
    `description`,
    `createTime`,
    `createBy`,
    `editTime`,
    `editBy`
  )
values
  (
    '4c33a084fe044646b631390280033c0d',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '动力节点CRM-P75-10',
    '2021-01-20',
    '2021-01-20',
    '0',
    '123',
    '2021-01-20 15:40:47',
    '张三',
    '2021-01-20 16:07:45',
    '张三'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd1f',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '动力节点CRM-P755',
    '2021-01-19',
    '2021-01-19',
    '0',
    '冲冲冲',
    '2021-01-19 01:10:54',
    '张三',
    '2021-01-20 16:21:30',
    '张三'
  ),
  (
    '84c5f525874244b6bd5a7c9bc73d36f9',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '动力节点CRM-P75',
    '2021-01-19',
    '2021-01-19',
    '0',
    '糖豆',
    '2021-01-20 16:21:45',
    '张三',
    '2021-01-20 17:13:37',
    '张三'
  ),
  (
    '8a359d8e74874c74ab8862329e3d581c',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '动力节点CRM-P100',
    '2021-02-10',
    '2021-02-11',
    '0',
    'abcd',
    '2021-02-10 18:31:22',
    '张三',
    NULL,
    NULL
  ),
  (
    'f7ec5b4787ac47c2b52e876c3e575fb5',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '动力节点CRM-P80',
    '2021-01-19',
    '2021-01-19',
    '0',
    '十天搞定CRM冲！',
    '2021-01-19 15:39:45',
    '张三',
    NULL,
    NULL
  );

COMMIT;