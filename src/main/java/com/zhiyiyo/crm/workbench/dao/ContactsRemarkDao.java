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
}
