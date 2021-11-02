package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.entity.CustomerRemark;
import com.zhiyiyo.crm.workbench.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Customer> getCustomerByCondition(Map<String, Object> condition);

    Integer getCustomerCountByCondition(Map<String, Object> condition);

    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    /**
     * 通过客户的 id 查询客户，客户的所有者是用户的 UUID 而不是用户名
     * @param id 客户 id
     * @return 客户
     */
    Customer getCustomerById(String id);

    /**
     * 通过客户的 id 查询客户，客户的所有者是用户名
     * @param id 客户 id
     * @return 客户
     */
    Customer getCustomer(String id);

    List<CustomerRemark> getRemarksByCId(String id);

    boolean addRemark(CustomerRemark remark);

    boolean updateRemark(CustomerRemark remark);

    boolean deleteRemark(String id);

    List<Transaction> getBoundTransactions(String customerId);

    List<Customer> getCustomersLikeName(String name);
}
