package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.dao.ActivityDao;
import com.zhiyiyo.crm.workbench.dao.ActivityRemarkDao;
import com.zhiyiyo.crm.workbench.dao.ClueActivityRelationDao;
import com.zhiyiyo.crm.workbench.dao.ContactsActivityRelationDao;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.ActivityRemark;
import com.zhiyiyo.crm.workbench.exception.ActivityException;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Override
    public boolean addActivity(Activity activity) {
        return activityDao.insert(activity).equals(1);
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return activityDao.update(activity).equals(1);
    }

    @Override
    @Transactional(rollbackFor = ActivityException.class)
    public boolean deleteActivities(String[] ids) throws ActivityException {
        Integer remarkCount = activityRemarkDao.queryRemarkCountByIds(ids);
        Integer deletedRemarkCount = activityRemarkDao.deleteRemarkByActivityIds(ids);
        Integer clueRelationCount = clueActivityRelationDao.queryCountByActivityIds(ids);
        Integer deletedClueRelationCount = clueActivityRelationDao.deleteByActivityIds(ids);
        Integer contactsRelationCount = contactsActivityRelationDao.queryCountByActivityIds(ids);
        Integer deletedContactsRelationCount = contactsActivityRelationDao.deleteByActivityIds(ids);
        Integer deletedActivityCount = activityDao.delete(ids);

        boolean success = deletedActivityCount.equals(ids.length)
                && remarkCount.equals(deletedRemarkCount)
                && clueRelationCount.equals(deletedClueRelationCount)
                && contactsRelationCount.equals(deletedContactsRelationCount);

        if (!success) {
            throw new ActivityException("删除市场活动失败");
        }

        return true;
    }

    @Override
    public PaginationVo<Activity> getActivitiesByCondition(Map<String, Object> condition) {

        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setCount(activityDao.queryActivityCountByCondition(condition));
        vo.setDataList(activityDao.queryActivitiesByCondition(condition));

        return vo;
    }

    @Override
    public List<Activity> getActivitiesByName(String name) {
        return activityDao.queryActivitiesByName(name);
    }

    @Override
    public Activity getActivity(String id) {
        return activityDao.queryActivity(id);
    }

    @Override
    public Activity getActivityById(String id) {
        return activityDao.queryActivityById(id);
    }

    @Override
    public List<ActivityRemark> getRemarksByAId(String id) {
        return activityRemarkDao.queryRemarksByAId(id);
    }

    @Override
    public boolean addRemark(ActivityRemark remark) {
        return activityRemarkDao.insertRemark(remark).equals(1);
    }

    @Override
    public boolean updateRemark(ActivityRemark remark) {
        return activityRemarkDao.updateRemark(remark).equals(1);
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkDao.deleteRemark(id).equals(1);
    }

}
