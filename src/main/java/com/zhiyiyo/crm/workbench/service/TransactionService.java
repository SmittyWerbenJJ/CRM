package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> getTransactionsByCondition(Map<String, Object> condition);

    Integer getTransactionCountByCondition(Map<String, Object> condition);
}
