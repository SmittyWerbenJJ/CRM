package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;

import java.util.List;

public interface ActivityService {
    /**
     * 添加一条市场活动信息
     *
     * @param activity 市场活动
     * @return 是否成功添加
     */
    boolean addActivity(Activity activity);

    /**
     * 更新一条市场活动信息
     *
     * @param activity 市场活动
     */
    boolean updateActivity(Activity activity);

    /**
     * 删除多条市场活动
     *
     * @param idList 市场活动列表
     */
    boolean deleteActivity(List<String> idList);

    /**
     * 根据条件查询市场活动
     *
     * @param pageNum   第几页
     * @param pageSize  每页显示的条目
     * @param name      市场活动名称
     * @param owner     所有者
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 分页结果
     */
    PaginationVo<Activity> getActivities(Integer pageNum, Integer pageSize, String name, String owner, String startDate,
            String endDate);
}
