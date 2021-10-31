package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.workbench.exception.ConvertException;
import com.zhiyiyo.crm.workbench.dao.*;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ClueRemarkDao clueRemarkDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    private ContactsDao contactsDao;

    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private CustomerRemarkDao customerRemarkDao;

    @Resource
    private TransactionDao transactionDao;

    @Resource
    private TransactionHistoryDao transactionHistoryDao;

    @Override
    public boolean addClue(Clue clue) {
        return clueDao.insertClue(clue).equals(1);
    }

    @Override
    public List<Clue> getCluesByCondition(Map<String, Object> condition) {
        return clueDao.queryCluesByCondition(condition);
    }

    @Override
    public Integer getClueCountByCondition(Map<String, Object> condition) {
        return clueDao.queryClueCountByCondition(condition);
    }

    @Override
    public Clue getClueById(String id) {
        return clueDao.queryClueById(id);
    }

    @Override
    public boolean addRemark(ClueRemark remark) {
        return clueRemarkDao.insertRemark(remark).equals(1);
    }

    @Override
    public List<ClueRemark> getRemarksByCId(String id) {
        return clueRemarkDao.queryRemarksByCId(id);
    }

    @Override
    public boolean updateRemark(ClueRemark remark) {
        return clueRemarkDao.updateRemark(remark).equals(1);
    }

    @Override
    public boolean deleteRemark(String id) {
        return clueRemarkDao.deleteRemark(id).equals(1);
    }

    @Override
    public List<Activity> getBoundActivities(String id) {
        return activityDao.queryActivitiesByClueId(id);
    }

    @Override
    public boolean unbindActivities(String[] ids) {
        boolean success = true;
        for (String id : ids) {
            success &= clueActivityRelationDao.deleteById(id).equals(1);
        }
        return success;
    }

    @Override
    public List<Activity> getUnboundActivities(Map<String, String> map) {
        return activityDao.queryUnboundClueActivities(map);
    }

    @Override
    public boolean bindActivities(String clueId, String[] activityIds) {
        List<ClueActivityRelation> relations = new ArrayList<>();

        for (String activityId : activityIds) {
            ClueActivityRelation relation = new ClueActivityRelation();
            relation.setId(UUIDUtil.getUUID());
            relation.setClueId(clueId);
            relation.setActivityId(activityId);
            relations.add(relation);
        }

        return clueActivityRelationDao.insertRelations(relations).equals(relations.size());
    }

    @Override
    public boolean updateClue(Clue clue) {
        return clueDao.updateClue(clue).equals(1);
    }

    @Override
    public boolean deleteClues(String[] ids) {
        Integer remarkCount = clueRemarkDao.queryRemarkCountByCIds(ids);
        Integer deletedRemarkCount = clueRemarkDao.deleteRemarkByCIds(ids);
        Integer relationCount = clueActivityRelationDao.queryCountByClueIds(ids);
        Integer deletedRelationCount = clueActivityRelationDao.deleteByClueIds(ids);
        Integer deletedClueCount = clueDao.deleteClues(ids);

        return remarkCount.equals(deletedRemarkCount)
                && relationCount.equals(deletedRelationCount)
                && deletedClueCount.equals(ids.length);
    }

    @Override
    @Transactional(rollbackFor = ConvertException.class)
    public boolean convert(String clueId, String createBy, Transaction tran) throws ConvertException {
        boolean success = true;

        // 查询出线索
        Clue clue = clueDao.queryClue(clueId);

        // 创建时间
        String createTime = DateTimeUtil.getSysTime();

        // 创建联系人
        Customer customer = new Customer();
        BeanUtils.copyProperties(clue, customer, "editBy", "editTime");
        customer.setId(UUIDUtil.getUUID());
        customer.setCreateTime(createTime);
        customer.setCreateBy(createBy);
        customer.setName(clue.getFullname());
        success &= customerDao.insert(customer).equals(1);

        // 查询出客户，如果客户不存在就新建一个
        Contacts contacts = contactsDao.queryContactsByName(clue.getCompany());
        if (contacts == null) {
            contacts = new Contacts();
            BeanUtils.copyProperties(clue, contacts, "editBy", "editTime", "fullname");
            contacts.setId(UUIDUtil.getUUID());
            contacts.setFullname(clue.getCompany());
            contacts.setCreateTime(createTime);
            contacts.setCreateBy(createBy);
            contacts.setCustomerId(customer.getId());
            success &= contactsDao.insert(contacts).equals(1);
        }

        // 获取线索的备注
        List<ClueRemark> clueRemarks = clueRemarkDao.queryRemarksByCId(clueId);
        List<ContactsRemark> contactsRemarks = new ArrayList<>();
        List<CustomerRemark> customerRemarks = new ArrayList<>();
        int remarkCount = clueRemarks.size();

        // 将线索的备注转换为联系人和客户的备注
        for (ClueRemark clueRemark : clueRemarks) {
            ContactsRemark contactsRemark = new ContactsRemark();
            BeanUtils.copyProperties(clueRemark, contactsRemark);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemarks.add(contactsRemark);

            CustomerRemark customerRemark = new CustomerRemark();
            BeanUtils.copyProperties(clueRemark, customerRemark);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCustomerId(customer.getId());
            customerRemarks.add(customerRemark);
        }

        success &= contactsRemarkDao.insertRemarks(contactsRemarks).equals(remarkCount);
        success &= customerRemarkDao.insertRemarks(customerRemarks).equals(remarkCount);

        // 将线索和市场活动的关系转换为联系人和市场活动的关系
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.queryRelationsByClueId(clueId);
        List<ContactsActivityRelation> contactsActivityRelations = new ArrayList<>();
        int relationCount = clueActivityRelations.size();

        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            contactsActivityRelations.add(contactsActivityRelation);
        }

        success &= contactsActivityRelationDao.insertRelations(contactsActivityRelations).equals(relationCount);

        // 创建交易和交易历史
        if (tran != null) {
            BeanUtils.copyProperties(clue, tran, "editTime", "editBy", "id", "name");
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
            tran.setContactsId(contacts.getId());
            tran.setCustomerId(customer.getId());
            success &= transactionDao.insert(tran).equals(1);

            TransactionHistory history = new TransactionHistory();
            BeanUtils.copyProperties(tran, history);
            history.setId(UUIDUtil.getUUID());
            history.setTranId(tran.getId());
            success &= transactionHistoryDao.insert(history).equals(1);
        }

        // 删除线索和市场活动的关联关系
        success &= clueActivityRelationDao.deleteByClueId(clueId).equals(relationCount);

        // 删除线索评论
        success &= clueRemarkDao.deleteRemarkByCId(clueId).equals(remarkCount);

        // 删除线索
        success &= clueDao.deleteClue(clueId).equals(1);

        if (!success) {
            throw new ConvertException("线索转换失败");
        }

        return success;
    }


}



























