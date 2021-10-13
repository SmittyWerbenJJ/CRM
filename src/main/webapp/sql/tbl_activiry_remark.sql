use crm;
DROP TABLE IF EXISTS `tbl_activity_remark`;
CREATE TABLE `tbl_activity_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `createTime` char(19) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL COMMENT '是否修改标志，0表示未修改，1表示已修改',
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
    `editFlag`,
    `activityId`
  )
values
  (
    '1c4595aa70b84c1dbf0ec05599d76e8d',
    '1234',
    '2021-01-24 02:24:34',
    '张三',
    '2021-01-24 02:24:37',
    '张三',
    '0',
    '84c5f525874244b6bd5a7c9bc73d36f9'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd11',
    '备注1（P75）',
    '2021-01-20 15:40:47',
    '张三',
    NULL,
    NULL,
    '0',
    '84c5f525874244b6bd5a7c9bc73d36f9'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd12',
    '备注2（P75）',
    '2021-01-20 16:40:47',
    '张三',
    NULL,
    NULL,
    '0',
    '84c5f525874244b6bd5a7c9bc73d36f9'
  ),
  (
    '6430a2a1c38c487ba5f323c344ffdd14',
    '备注4（P75）',
    '2021-01-20 18:40:47',
    '张三',
    NULL,
    NULL,
    '0',
    'f7ec5b4787ac47c2b52e876c3e575fb5'
  ),
  (
    '8d0a046c168744d78f0e8fcb1c48454d',
    '备注3',
    '2021-01-24 01:09:06',
    '张三',
    NULL,
    NULL,
    '0',
    '84c5f525874244b6bd5a7c9bc73d36f9'
  );