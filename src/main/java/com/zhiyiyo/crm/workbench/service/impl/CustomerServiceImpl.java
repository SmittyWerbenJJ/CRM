package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.*;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.entity.CustomerRemark;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.exception.CustomerException;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Resource
    private CustomerRemarkDao customerRemarkDao;

    @Resource
    private ContactsDao contactsDao;

    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    private TransactionDao transactionDao;

    @Resource
    private TransactionRemarkDao transactionRemarkDao;

    @Resource
    private TransactionHistoryDao transactionHistoryDao;

    @Override
    public List<Customer> getCustomerByCondition(Map<String, Object> condition) {
        return customerDao.queryCustomerByCondition(condition);
    }

    @Override
    public Integer getCustomerCountByCondition(Map<String, Object> condition) {
        return customerDao.queryCustomerCountByCondition(condition);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDao.insert(customer).equals(1);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerDao.queryCustomerById(id);
    }

    @Override
    public Customer getCustomer(String id) {
        return customerDao.queryCustomer(id);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.update(customer).equals(1);
    }

    @Override
    public List<CustomerRemark> getRemarksByCId(String id) {
        return customerRemarkDao.queryRemarksByCId(id);
    }

    @Override
    public boolean addRemark(CustomerRemark remark) {
        return customerRemarkDao.insert(remark).equals(1);
    }

    @Override
    public boolean updateRemark(CustomerRemark remark) {
        return customerRemarkDao.update(remark).equals(1);
    }

    @Override
    public boolean deleteRemark(String id) {
        return customerRemarkDao.delete(id).equals(1);
    }

    @Override
    public List<Transaction> getBoundTransactions(String customerId) {
        return transactionDao.queryTransactionsByCustomerId(customerId);
    }

    @Override
    public List<Customer> getCustomersLikeName(String name) {
        return customerDao.queryCustomersLikeName(name);
    }

    @Override
    @Transactional(rollbackFor = CustomerException.class)
    public boolean deleteCustomers(String[] ids) throws CustomerException {
        boolean success = true;

        // 删除交易、交易历史和交易评论
        Integer historyCount = transactionHistoryDao.queryCountByTransactionIds(ids);
        Integer deletedHistoryCount = transactionHistoryDao.deleteByTransactionIds(ids);
        Integer tranRemarkCount = transactionRemarkDao.queryCountByTransactionId(ids);
        Integer deletedTranRemarkCount = transactionRemarkDao.deleteByTransactionId(ids);
        Integer tranCount = transactionDao.queryCountByCustomerIds(ids);
        Integer deletedTranCount = transactionDao.deleteByCustomerIds(ids);
        success = tranCount.equals(deletedTranCount)
                && historyCount.equals(deletedHistoryCount)
                && tranRemarkCount.equals(deletedTranRemarkCount);

        // 删除联系人、联系人评论和联系人与市场活动的关系
        Integer contactsRemarkCount = contactsRemarkDao.queryCountByContactsIds(ids);
        Integer deletedContactsRemarkCount = contactsRemarkDao.deleteByContactsIds(ids);
        Integer relationCount = contactsActivityRelationDao.queryCountByContactsIds(ids);
        Integer deletedRelationCount = contactsActivityRelationDao.deleteByContactsIds(ids);
        Integer contactsCount = contactsDao.queryCountByCustomerIds(ids);
        Integer deletedContactsCount = contactsDao.deleteByCustomerIds(ids);
        success &= contactsCount.equals(deletedContactsCount)
                && contactsRemarkCount.equals(deletedContactsRemarkCount)
                && relationCount.equals(deletedRelationCount);

        // 删除客户和客户评论
        Integer customerRemarkCount = customerRemarkDao.queryCountByCustomerIds(ids);
        Integer deletedCustomerRemarkCount = customerRemarkDao.deleteByCustomerIds(ids);
        Integer deletedCustomerCount = customerDao.deleteByIds(ids);
        success &= deletedCustomerCount.equals(ids.length)
                && deletedCustomerRemarkCount.equals(customerRemarkCount);

        if (!success) {
            throw new CustomerException("删除联系人失败");
        }

        return true;
    }
}
