use crm;
DROP TABLE IF EXISTS `tbl_customer_remark`;
CREATE TABLE `tbl_customer_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL,
  `customerId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*Data for the table `tbl_customer_remark` */
insert into
  `tbl_customer_remark`(
    `id`,
    `noteContent`,
    `createBy`,
    `createTime`,
    `editBy`,
    `editTime`,
    `editFlag`,
    `customerId`
  )
values
  (
    '179a37c58a964d0dbbf0981a9dd8c42e',
    '备注3（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    '9cb43d197d5a4df8ae58f27e0a216bf7'
  ),
  (
    '1c7412d9bd9e4b44889337aa18b2af79',
    '备注2（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    '9cb43d197d5a4df8ae58f27e0a216bf7'
  ),
  (
    '251c2ed3e33e4e9b9a43ef0e039799b2',
    '备注4（属于王健林）',
    '张三',
    '2021-01-28 02:13:40',
    NULL,
    NULL,
    '0',
    '995e231cce32456d9cf3d1c2e6f287c0'
  ),
  (
    'ebeda91a851f4b3cbc55c5b002f3378b',
    '备注1（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    '9cb43d197d5a4df8ae58f27e0a216bf7'
  );