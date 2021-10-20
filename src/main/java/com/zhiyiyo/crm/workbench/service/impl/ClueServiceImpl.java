package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.ClueDao;
import com.zhiyiyo.crm.workbench.dao.ClueRemarkDao;
import com.zhiyiyo.crm.workbench.entity.Clue;
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
    private ClueRemarkDao clueRemarkDao;

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
}
