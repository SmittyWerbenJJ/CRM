package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    List<Contacts> getContactsByCondition(Map<String, Object> condition);

    Integer getContactsCountByCondition(Map<String, Object> condition);
}
