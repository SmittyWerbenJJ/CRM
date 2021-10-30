package com.zhiyiyo.crm.workbench.dao;


import com.zhiyiyo.crm.workbench.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionDao {
    List<Transaction> queryTransactionsByCondition(Map<String, Object> condition);

    Integer queryTransactionCountByCondition(Map<String, Object> condition);
}
