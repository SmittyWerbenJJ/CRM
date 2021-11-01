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

    boolean addContacts(Contacts contacts);

    Contacts getContactsById(String id);

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
}
