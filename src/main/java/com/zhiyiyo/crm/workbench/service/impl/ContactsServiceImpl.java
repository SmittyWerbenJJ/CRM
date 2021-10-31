package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.ContactsDao;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    private ContactsDao contactsDao;

    @Override
    public List<Contacts> getContactsByCondition(Map<String, Object> condition) {
        return contactsDao.queryContactsByCondition(condition);
    }

    @Override
    public Integer getContactsCountByCondition(Map<String, Object> condition) {
        return contactsDao.queryContactsCountByCondition(condition);
    }

    @Override
    public boolean addContacts(Contacts contacts) {
        return contactsDao.insert(contacts).equals(1);
    }
}
