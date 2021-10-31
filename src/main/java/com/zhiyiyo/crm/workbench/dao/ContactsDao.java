package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {
    List<Contacts> queryContactsByCondition(Map<String, Object> condition);

    Integer queryContactsCountByCondition(Map<String, Object> condition);

    /**
     * 插入一个联系人
     * @param contacts 联系人
     * @return 受影响的行数
     */
    Integer insert(Contacts contacts);
}
