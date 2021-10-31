package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    List<Customer> queryCustomerByCondition(Map<String, Object> condition);

    Integer queryCustomerCountByCondition(Map<String, Object> condition);

    /**
     * 通过客户名称精确查找客户
     * @param name 客户名称
     * @return 客户
     */
    Customer queryCustomerByName(String name);

    /**
     * 插入一个客户
     * @param customer 客户
     * @return 受影响的行数
     */
    Integer insert(Customer customer);
}
