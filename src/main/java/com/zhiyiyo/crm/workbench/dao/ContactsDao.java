package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {
    List<Contacts> queryContactsByCondition(Map<String, Object> condition);

    Integer queryContactsCountByCondition(Map<String, Object> condition);

    /**
     * 通过客户名称精确查询客户
     * @param name 客户名称
     * @return 客户
     */
    Contacts queryContactsByName(String name);

    /**
     * 插入一个顾客
     * @param contacts 顾客
     * @return 受影响的行数
     */
    Integer insert(Contacts contacts);
}
