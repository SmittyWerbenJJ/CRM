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
    '40f6cdea0bd34aceb77492a1656d9fb4',
    '发动力节点传单',
    '2021-10-15',
    '2021-10-15',
    '1000',
    '好累啊',
    '2021-10-16 15:40:47',
    '之一Yo',
    '2021-10-16 16:07:45',
    '张三'
  ),
  (
    '4c33a084fe044646b631390280033c0e',
    '40f6cdea0bd34aceb77492a1656d9fb4',
    '发百度传单',
    '2021-10-16',
    '2021-10-16',
    '1200',
    '好尴尬啊',
    '2021-10-17 15:40:47',
    '之一Yo',
    '2021-10-17 16:07:45',
    '张三'
  ),
  (
    '4c33a084fe044646b631390280033c1e',
    '40f6cdea0bd34aceb77492a1656d9fb4',
    '宣讲会',
    '2021-10-17',
    '2021-10-17',
    '1200',
    '场面一度十分尴尬',
    '2021-10-18 15:40:47',
    '之一Yo',
    NULL,
    NULL
  ),
  (
    '4c33a084fe044646b631390280033c2e',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '演讲',
    '2021-10-17',
    '2021-10-17',
    '3000',
    '十分尴尬',
    '2021-10-18 15:40:47',
    '张三',
    NULL,
    NULL
  ),
  (
    '4c33a084fe044646b631390280033c3e',
    '40f6cdea0bd34aceb77492a1656d9fb3',
    '演讲',
    '2021-10-17',
    '2021-10-17',
    '3000',
    '十分尴尬',
    '2021-10-18 15:40:47',
    '张三',
    NULL,
    NULL
  );

COMMIT;