package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.TransactionDao;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private TransactionDao transactionDao;

    @Override
    public List<Transaction> getTransactionsByCondition(Map<String, Object> condition) {
        return transactionDao.queryTransactionsByCondition(condition);
    }

    @Override
    public Integer getTransactionCountByCondition(Map<String, Object> condition) {
        return transactionDao.queryTransactionCountByCondition(condition);
    }
}
