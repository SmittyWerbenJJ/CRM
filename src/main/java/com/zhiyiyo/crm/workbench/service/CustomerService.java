package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Customer> getCustomerByCondition(Map<String, Object> condition);

    Integer getCustomerCountByCondition(Map<String, Object> condition);
}
