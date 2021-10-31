package com.zhiyiyo.crm.workbench.dao;


import com.zhiyiyo.crm.workbench.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionDao {
    List<Transaction> queryTransactionsByCondition(Map<String, Object> condition);

    Integer queryTransactionCountByCondition(Map<String, Object> condition);

    /**
     * 插入一条交易
     * @param tran 交易
     * @return 受影响的行数
     */
    Integer insert(Transaction tran);
}
