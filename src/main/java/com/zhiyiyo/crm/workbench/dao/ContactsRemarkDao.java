package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.ContactsRemark;

import java.util.List;

public interface ContactsRemarkDao {

    /**
     * 插入多条评论
     * @param remarks 联系人评论
     * @return 受影响的行数
     */
    Integer insertRemarks(List<ContactsRemark> remarks);

    /**
     * 插入一条评论
     * @param remark 联系人评论
     * @return 受影响的行数
     */
    Integer insert(ContactsRemark remark);

    /**
     * 通过联系人 id 获取评论
     * @param id 联系人 id
     * @return 评论列表
     */
    List<ContactsRemark> queryRemarksByContactsId(String id);

    Integer update(ContactsRemark remark);

    Integer delete(String id);

    Integer queryCountByContactsIds(String[] ids);

    Integer deleteByContactsIds(String[] ids);
}
