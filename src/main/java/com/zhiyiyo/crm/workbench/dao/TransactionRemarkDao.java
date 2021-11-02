package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.TransactionRemark;

import java.util.List;

public interface TransactionRemarkDao {
    Integer insert(TransactionRemark remark);

    List<TransactionRemark> queryRemarksByTransactionId(String id);

    Integer update(TransactionRemark remark);

    Integer delete(String id);
}
