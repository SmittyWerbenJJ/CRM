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