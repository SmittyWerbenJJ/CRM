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

    /**
     * 通过联系人 id 获取交易
     * @param contactsId 联系人 id
     * @return 交易列表
     */
    List<Transaction> queryTransactionsByContactsId(String contactsId);

    /**
     * 通过客户 id 获取交易
     * @param customerId 客户 id
     * @return 交易列表
     */
    List<Transaction> queryTransactionsByCustomerId(String customerId);

    /**
     * 通过交易的 id 查询交易其中 <code>customerId</code>、
     * <code>activityId</code>、<code>contactsId</code> 都是名字而不是 UUID
     * @param id 交易 id
     * @return 交易
     */
    Transaction queryTransactionById(String id);

    Integer updateStage(Transaction tran);

    Integer update(Transaction tran);

    /**
     * 通过交易的 id 查询交易其中 <code>customerId</code>、
     * <code>activityId</code>、<code>contactsId</code> 都是 UUID
     * @param id 交易 id
     * @return 交易
     */
    Transaction queryTransaction(String id);

    Integer deleteByIds(String[] ids);

    Integer queryCountByCustomerIds(String[] ids);

    Integer deleteByCustomerIds(String[] ids);
}
