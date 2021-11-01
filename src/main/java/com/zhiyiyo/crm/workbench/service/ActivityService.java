package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.ActivityRemark;
import com.zhiyiyo.crm.workbench.exception.ActivityException;

import java.util.List;
import java.util.Map;

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
     * @param ids 市场活动列表
     * @exception ActivityException 删除市场活动失败时抛出异常
     */
    boolean deleteActivities(String[] ids) throws ActivityException;

    /**
     * 根据条件查询市场活动
     *
     * @param condition 查询条件字典
     * @return 分页结果
     */
    PaginationVo<Activity> getActivitiesByCondition(Map<String, Object> condition);

    /**
     * 通过市场活动名称查询市场活动
     * @param name 市场活动名称
     * @return 市场活动列表
     */
    List<Activity> getActivitiesByName(String name);

    /**
     * 根据 id 获取一条市场活动信息
     * @param id 市场活动的 id，市场活动的 owner 是用户的 UUID
     * @return 市场活动
     */
    Activity getActivity(String id);

    /**
     * 根据 id 获取一条市场活动信息，市场活动的 owner 是用户名
     * @param id 市场活动的 id
     * @return 市场活动
     */
    Activity getActivityById(String id);


    /**
     * 通过市场活动的 id 获取评价列表
     * @param id 市场活动 id
     * @return 评价列表
     */
    List<ActivityRemark> getRemarksByAId(String id);

    /**
     * 添加市场活动的评论
     * @param remark 评论
     * @return 添加是否成功
     */
    boolean addRemark(ActivityRemark remark);

    /**
     * 更新市场活动评论
     * @param remark 评论
     * @return 更新是否成功
     */
    boolean updateRemark(ActivityRemark remark);

    /**
     * 删除一条市场活动备注
     * @param id 备注的 id
     * @return 删除是否成功
     */
    boolean deleteRemark(String id);
}
