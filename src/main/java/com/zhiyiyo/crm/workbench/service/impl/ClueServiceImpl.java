package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.workbench.dao.ClueDao;
import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;

    @Override
    public boolean addClue(Clue clue) {
        return clueDao.insertClue(clue).equals(1);
    }
}
