package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.workbench.dao.*;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    private ContactsDao contactsDao;

    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    private TransactionDao transactionDao;

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

    @Override
    public Contacts getContactsById(String id) {
        return contactsDao.getContactsById(id);
    }

    @Override
    public List<ContactsRemark> getRemarksByCId(String id) {
        return contactsRemarkDao.getRemarksByCId(id);
    }

    @Override
    public boolean addRemark(ContactsRemark remark) {
        return contactsRemarkDao.insert(remark).equals(1);
    }

    @Override
    public boolean updateRemark(ContactsRemark remark) {
        return contactsRemarkDao.update(remark).equals(1);
    }

    @Override
    public boolean deleteRemark(String id) {
        return contactsRemarkDao.delete(id).equals(1);
    }

    @Override
    public List<Activity> getBoundActivities(String contactsId) {
        return activityDao.queryActivitiesByContactsId(contactsId);
    }

    @Override
    public List<Activity> getUnboundActivities(Map<String, String> map) {
        return activityDao.queryUnboundContactsActivities(map);
    }

    @Override
    public boolean bindActivities(String contactsId, String[] activityIds) {
        List<ContactsActivityRelation> relations = new ArrayList<>();

        for (String activityId : activityIds) {
            ContactsActivityRelation relation = new ContactsActivityRelation();
            relation.setId(UUIDUtil.getUUID());
            relation.setContactsId(contactsId);
            relation.setActivityId(activityId);
            relations.add(relation);
        }

        return contactsActivityRelationDao.insertRelations(relations).equals(relations.size());
    }

    @Override
    public boolean unbindActivities(String[] ids) {
        return contactsActivityRelationDao.deleteByIds(ids).equals(ids.length);
    }

    @Override
    public List<Transaction> getBoundTransactions(String contactsId) {
        return transactionDao.queryTransactionsByContactsId(contactsId);
    }

    @Override
    public List<Contacts> getContactsByCustomerId(String customerId) {
        return contactsDao.queryContactsByCustomerId(customerId);
    }

    @Override
    public List<Contacts> getContactsByName(String name) {
        return contactsDao.queryContactsByName(name);
    }
}
