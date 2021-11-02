package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.entity.TransactionHistory;
import com.zhiyiyo.crm.workbench.entity.TransactionRemark;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> getTransactionsByCondition(Map<String, Object> condition);

    Integer getTransactionCountByCondition(Map<String, Object> condition);

    boolean addTransaction(Transaction tran, String customerName);

    Transaction getTransactionById(String id);

    boolean addRemark(TransactionRemark remark);

    List<TransactionRemark> getRemarksByTransactionId(String id);

    boolean updateRemark(TransactionRemark remark);

    boolean deleteRemark(String id);

    List<TransactionHistory> getHistories(String id);

    boolean updateStage(Transaction tran);
}
