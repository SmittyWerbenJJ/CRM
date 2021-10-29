DROP TABLE IF EXISTS `tbl_contacts_remark`;
CREATE TABLE `tbl_contacts_remark` (
  `id` char(32) NOT NULL,
  `noteContent` varchar(255) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  `createTime` char(19) DEFAULT NULL,
  `editBy` varchar(255) DEFAULT NULL,
  `editTime` char(19) DEFAULT NULL,
  `editFlag` char(1) DEFAULT NULL,
  `contactsId` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*Data for the table `tbl_contacts_remark` */
insert into
  `tbl_contacts_remark`(
    `id`,
    `noteContent`,
    `createBy`,
    `createTime`,
    `editBy`,
    `editTime`,
    `editFlag`,
    `contactsId`
  )
values
  (
    '2507e63b387042a6a35b9c4828aaad30',
    '备注3（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    'f813162a9a6445de8ceb2b6115c8a27b'
  ),
  (
    '3b5e195c99c6405e905f426926a7c2df',
    '备注4（属于王健林）',
    '张三',
    '2021-01-28 02:13:40',
    NULL,
    NULL,
    '0',
    'fadbec6aa3774bf2b8782e5a2d3af153'
  ),
  (
    'b1cee005a89d40f08d9db2ea39b88845',
    '备注1（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    'f813162a9a6445de8ceb2b6115c8a27b'
  ),
  (
    'b312051776414312805614602f9df80c',
    '备注2（属于马云）',
    '张三',
    '2021-01-28 02:00:02',
    NULL,
    NULL,
    '0',
    'f813162a9a6445de8ceb2b6115c8a27b'
  );