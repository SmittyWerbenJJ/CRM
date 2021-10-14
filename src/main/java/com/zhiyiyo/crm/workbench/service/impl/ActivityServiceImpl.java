package com.zhiyiyo.crm.workbench.service.impl;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.dao.ActivityDao;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;

    @Override
    public boolean addActivity(Activity activity) {
        return activityDao.insert(activity) == 1;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return activityDao.update(activity) == 1;
    }

    @Override
    public boolean deleteActivity(List<String> idList) {
        return activityDao.delete(idList) == idList.size();
    }

    @Override
    public PaginationVo<Activity> getActivities(Integer pageNum, Integer pageSize,  String name, String owner, String startDate, String endDate) {
        int start = (pageNum - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("pageSize", pageSize);
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setCount(activityDao.queryActivityCount(map));
        vo.setDataList(activityDao.queryActivities(map));

        return vo;
    }
}
