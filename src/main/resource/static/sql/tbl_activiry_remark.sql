use crm;
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
/*Data for the table `tbl_activity_remark` */
insert into
  `tbl_activity_remark`(
    `id`,
    `noteContent`,
    `createTime`,
    `createBy`,
    `editTime`,
    `editBy`,
    `activityId`
  )
values
  (
    '1c4595aa70b84c1dbf0ec05599d76e8d',
    '哈哈哈',
    '2021-10-15 02:24:34',
    '张三',
    '2021-10-15 02:24:37',
    '张三',
    '4c33a084fe044646b631390280033c0d'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd11',
    '可以啊',
    '2021-01-20 15:40:47',
    '李四',
    NULL,
    NULL,
    '4c33a084fe044646b631390280033c0d'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd12',
    '好累呀',
    '2021-01-20 16:40:47',
    '之一Yo',
    NULL,
    NULL,
    '4c33a084fe044646b631390280033c0d'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd13',
    '哎呦不错哦',
    '2021-01-20 16:40:47',
    '李四',
    NULL,
    NULL,
    '4c33a084fe044646b631390280033c0e'
  );