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

    /**
     * 根据交易的 id 查询交易，其中 <code>customerId</code>、
     * <code>activityId</code>、<code>contactsId</code> 都是名字而不是 UUID
     * @param id 交易 id
     * @return 交易信息
     */
    Transaction getTransactionById(String id);

    /**
     * 根据交易的 id 查询交易，其中 <code>customerId</code>、
     * <code>activityId</code>、<code>contactsId</code> 都是 UUID
     * @param id 交易 id
     * @return 交易信息
     */
    Transaction getTransaction(String id);

    boolean addRemark(TransactionRemark remark);

    List<TransactionRemark> getRemarksByTransactionId(String id);

    boolean updateRemark(TransactionRemark remark);

    boolean deleteRemark(String id);

    List<TransactionHistory> getHistories(String id);

    boolean updateStage(Transaction tran);

    boolean updateTransaction(Transaction tran, String customerName);

    boolean deleteTransactions(String[] ids);
}
