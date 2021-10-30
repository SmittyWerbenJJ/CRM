package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.CustomerDao;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Override
    public List<Customer> getCustomerByCondition(Map<String, Object> condition) {
        return customerDao.queryCustomerByCondition(condition);
    }

    @Override
    public Integer getCustomerCountByCondition(Map<String, Object> condition) {
        return customerDao.queryCustomerCountByCondition(condition);
    }
}
