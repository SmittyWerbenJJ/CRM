package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.entity.ContactsRemark;
import com.zhiyiyo.crm.workbench.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    List<Contacts> getContactsByCondition(Map<String, Object> condition);

    Integer getContactsCountByCondition(Map<String, Object> condition);

    /**
     * 创建一个联系人，如果客户名称不存在就新建一个客户
     * @param contacts 联系人
     * @param customerName 客户名称
     * @return 创建是否成功
     */
    boolean addContacts(Contacts contacts, String customerName);

    /**
     * 创建一个联系人
     * @param contacts 联系人
     * @return 创建是否成功
     */
    boolean addContacts(Contacts contacts);

    /**
     * 通过联系人 id 获取联系人信息，其中 <code>customerId</code> 是顾客名而不是 UUID
     * @param id 联系人 id
     * @return 联系人
     */
    Contacts getContactsById(String id);

    /**
     * 通过联系人 id 获取联系人信息，其中 <code>customerId</code> 是 UUID
     * @param id 联系人 id
     * @return 联系人
     */
    Contacts getContacts(String id);

    List<ContactsRemark> getRemarksByCId(String id);

    boolean addRemark(ContactsRemark remark);

    boolean updateRemark(ContactsRemark remark);

    boolean deleteRemark(String id);

    List<Activity> getBoundActivities(String contactsId);

    List<Activity> getUnboundActivities(Map<String, String> map);

    boolean bindActivities(String contactsId, String[] activityIds);

    boolean unbindActivities(String[] ids);

    List<Transaction> getBoundTransactions(String contactsId);

    List<Contacts> getContactsByCustomerId(String customerId);

    List<Contacts> getContactsByName(String name);

    boolean updateContacts(Contacts contacts, String customerName);

    boolean deleteContacts(String[] ids);
}
