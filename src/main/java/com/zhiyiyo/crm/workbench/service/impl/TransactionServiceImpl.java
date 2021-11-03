package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.workbench.dao.CustomerDao;
import com.zhiyiyo.crm.workbench.dao.TransactionDao;
import com.zhiyiyo.crm.workbench.dao.TransactionHistoryDao;
import com.zhiyiyo.crm.workbench.dao.TransactionRemarkDao;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.entity.TransactionHistory;
import com.zhiyiyo.crm.workbench.entity.TransactionRemark;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private TransactionDao transactionDao;

    @Resource
    private TransactionRemarkDao transactionRemarkDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private TransactionHistoryDao transactionHistoryDao;

    @Override
    public List<Transaction> getTransactionsByCondition(Map<String, Object> condition) {
        return transactionDao.queryTransactionsByCondition(condition);
    }

    @Override
    public Integer getTransactionCountByCondition(Map<String, Object> condition) {
        return transactionDao.queryTransactionCountByCondition(condition);
    }

    @Override
    public boolean addTransaction(Transaction tran, String customerName) {
        boolean success = true;

        // 如果客户不存在则新建
        Customer customer = customerDao.queryCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            BeanUtils.copyProperties(tran, customer);
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            success &= customerDao.insert(customer).equals(1);
        }

        tran.setCustomerId(customer.getId());

        // 创建交易历史
        TransactionHistory history = new TransactionHistory();
        BeanUtils.copyProperties(tran, history);
        history.setId(UUIDUtil.getUUID());
        history.setTranId(tran.getId());

        success &= transactionDao.insert(tran).equals(1);
        success &= transactionHistoryDao.insert(history).equals(1);

        return success;
    }

    @Override
    public Transaction getTransactionById(String id) {
        return transactionDao.queryTransactionById(id);
    }

    @Override
    public Transaction getTransaction(String id) {
        return transactionDao.queryTransaction(id);
    }

    @Override
    public boolean addRemark(TransactionRemark remark) {
        return transactionRemarkDao.insert(remark).equals(1);
    }

    @Override
    public List<TransactionRemark> getRemarksByTransactionId(String id) {
        return transactionRemarkDao.queryRemarksByTransactionId(id);
    }

    @Override
    public boolean updateRemark(TransactionRemark remark) {
        return transactionRemarkDao.update(remark).equals(1);
    }

    @Override
    public boolean deleteRemark(String id) {
        return transactionRemarkDao.delete(id).equals(1);
    }

    @Override
    public List<TransactionHistory> getHistories(String id) {
        return transactionHistoryDao.queryHistoriesByTransactionId(id);
    }

    @Override
    public boolean updateStage(Transaction tran) {
        boolean success = true;

        // 更新交易信息
        success &= transactionDao.updateStage(tran).equals(1);

        // 创建交易历史
        TransactionHistory history = new TransactionHistory();
        history.setId(UUIDUtil.getUUID());
        history.setTranId(tran.getId());
        history.setMoney(tran.getMoney());
        history.setStage(tran.getStage());
        history.setExpectedDate(tran.getExpectedDate());
        history.setCreateBy(tran.getEditBy());
        history.setCreateTime(tran.getEditTime());
        success &= transactionHistoryDao.insert(history).equals(1);

        return success;
    }

    @Override
    public boolean updateTransaction(Transaction tran, String customerName) {
        boolean success = true;

        // 如果客户不存在则新建
        Customer customer = customerDao.queryCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            BeanUtils.copyProperties(tran, customer);
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            success &= customerDao.insert(customer).equals(1);
        }

        tran.setCustomerId(customer.getId());
        success &= transactionDao.update(tran).equals(1);
        return success;
    }

    @Override
    public boolean deleteTransactions(String[] ids) {
        boolean success;

        // 删除交易历史
        Integer historyCount = transactionHistoryDao.queryCountByTransactionIds(ids);
        Integer deletedHistoryCount = transactionHistoryDao.deleteByTransactionIds(ids);

        // 删除交易评论
        Integer remarkCount = transactionRemarkDao.queryCountByTransactionId(ids);
        Integer deletedRemarkCount = transactionRemarkDao.deleteByTransactionId(ids);

        // 删除交易
        Integer deletedTransactionCount = transactionDao.deleteByIds(ids);

        success = deletedTransactionCount.equals(ids.length)
                && historyCount.equals(deletedHistoryCount)
                && remarkCount.equals(deletedRemarkCount);

        return success;
    }

}




























