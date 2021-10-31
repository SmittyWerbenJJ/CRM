package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.TransactionHistory;

public interface TransactionHistoryDao {

    /**
     * 插入一条交易记录
     * @param history 交易记录
     * @return 受影响的行数
     */
    Integer insert(TransactionHistory history);
}
