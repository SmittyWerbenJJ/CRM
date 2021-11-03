package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.TransactionHistory;

import java.util.List;

public interface TransactionHistoryDao {

    /**
     * 插入一条交易记录
     * @param history 交易记录
     * @return 受影响的行数
     */
    Integer insert(TransactionHistory history);

    List<TransactionHistory> queryHistoriesByTransactionId(String id);

    Integer queryCountByTransactionIds(String[] ids);

    Integer deleteByTransactionIds(String[] ids);
}
