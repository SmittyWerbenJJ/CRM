package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    List<Customer> queryCustomerByCondition(Map<String, Object> condition);

    Integer queryCustomerCountByCondition(Map<String, Object> condition);
}
