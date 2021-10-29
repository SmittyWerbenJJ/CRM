use crm;
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
    '920deda5c9df2f9c7f81b1f3ae47db8d', /* 666666 */
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
    '7e1d774ea263041a26945672cb9b63c2', /* 101010 */
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
    "0ebb84d6da26e749277b41782f18097d", /* 123456 */
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