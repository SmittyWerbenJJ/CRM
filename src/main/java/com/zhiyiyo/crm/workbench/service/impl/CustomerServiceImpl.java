package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.CustomerDao;
import com.zhiyiyo.crm.workbench.dao.CustomerRemarkDao;
import com.zhiyiyo.crm.workbench.dao.TransactionDao;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.entity.CustomerRemark;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

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
    private TransactionDao transactionDao;

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
}
