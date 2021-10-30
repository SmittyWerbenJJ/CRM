package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.workbench.dao.ActivityDao;
import com.zhiyiyo.crm.workbench.dao.ClueActivityRelationDao;
import com.zhiyiyo.crm.workbench.dao.ClueDao;
import com.zhiyiyo.crm.workbench.dao.ClueRemarkDao;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.entity.ClueActivityRelation;
import com.zhiyiyo.crm.workbench.entity.ClueRemark;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public ClueActivityRelationDao clueActivityRelationDao;

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
        boolean success = true;

        for (String activityId : activityIds) {
            ClueActivityRelation relation = new ClueActivityRelation();
            relation.setId(UUIDUtil.getUUID());
            relation.setClueId(clueId);
            relation.setActivityId(activityId);
            success &= clueActivityRelationDao.insertRelation(relation).equals(1);
        }

        return success;
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
}
