package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.workbench.dao.*;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import org.springframework.beans.BeanUtils;
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
    private CustomerDao customerDao;

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
    public boolean addContacts(Contacts contacts, String customerName) {
        boolean success = true;

        // 如果客户不存在则新建
        Customer customer = customerDao.queryCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            BeanUtils.copyProperties(contacts, customer);
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            success &= customerDao.insert(customer).equals(1);
        }

        contacts.setCustomerId(customer.getId());
        success &= contactsDao.insert(contacts).equals(1);
        return success;
    }

    @Override
    public boolean addContacts(Contacts contacts) {
        return contactsDao.insert(contacts).equals(1);
    }

    @Override
    public Contacts getContactsById(String id) {
        return contactsDao.queryContactsById(id);
    }

    @Override
    public Contacts getContacts(String id) {
        return contactsDao.queryContacts(id);
    }

    @Override
    public List<ContactsRemark> getRemarksByCId(String id) {
        return contactsRemarkDao.queryRemarksByContactsId(id);
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

    @Override
    public boolean updateContacts(Contacts contacts, String customerName) {
        boolean success = true;

        // 如果客户不存在则新建
        Customer customer = customerDao.queryCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            BeanUtils.copyProperties(contacts, customer);
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            success &= customerDao.insert(customer).equals(1);
        }

        contacts.setCustomerId(customer.getId());
        success &= contactsDao.update(contacts).equals(1);
        return success;
    }

    @Override
    public boolean deleteContacts(String[] ids) {
        // 删除评论
        Integer remarkCount = contactsRemarkDao.queryCountByContactsIds(ids);
        Integer deletedRemarkCount = contactsRemarkDao.deleteByContactsIds(ids);

        // 删除联系人和市场活动的关联
        Integer relationCount = contactsActivityRelationDao.queryCountByContactsIds(ids);
        Integer deletedRelationCount = contactsActivityRelationDao.deleteByContactsIds(ids);

        // 删除联系人
        Integer deletedContactsCount = contactsDao.deleteByIds(ids);

        return deletedContactsCount.equals(ids.length)
                && remarkCount.equals(deletedRemarkCount)
                && relationCount.equals(deletedRelationCount);
    }
}
