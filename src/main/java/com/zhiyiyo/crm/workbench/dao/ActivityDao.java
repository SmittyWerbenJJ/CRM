package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Activity;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    /**
     * 插入一条市场活动记录
     *
     * @param activity 市场活动
     * @return 受影响的行数
     */
    Integer insert(Activity activity);

    /**
     * 删除多条市场活动记录
     *
     * @param ids 市场活动 id 列表
     * @return 受影响的行数
     */
    Integer delete(String[] ids);

    /**
     * 查询符合匹配条件的市场活动总数
     *
     * @return 总条数
     */
    Integer queryActivityCountByCondition(Map<String,Object> condition);

    /**
     * 查询符合匹配条件的市场活动
     *
     * @return 符合匹配条件的分页查询结果
     */
    List<Activity> queryActivitiesByCondition(Map<String, Object> condition);

    /**
     * 通过名称查询市场活动
     * @param name 市场活动名称
     * @return 市场活动列表
     */
    List<Activity> queryActivitiesByName(String name);

    /**
     * 根据 id 获取一条市场活动信息，此时返回的 owner 是 UUID 序号而不是名字
     *
     * @return 市场活动信息
     */
    Activity queryActivity(String id);

    /**
     * 根据 id 获取一条市场活动信息，此时返回的 owner 是名字而不是 UUID 序号
     *
     * @return 市场活动信息
     */
    Activity queryActivityById(String id);

    /**
     * 更新市场活动信息
     * @param activity 市场活动信息
     * @return 受影响的行数
     */
    Integer update(Activity activity);

    /**
     * 查询所有和指定线索 id 相关联的市场活动
     * @param id 线索 id
     * @return 市场活动列表
     */
    List<Activity> queryActivitiesByClueId(String id);


    /**
     * 获取还没和线索绑定的市场活动
     * @param map 市场活动名称和线索 id 组成的字典
     * @return 匹配名字的市场活动列表
     */
    List<Activity> queryUnboundClueActivities(Map<String, String> map);
}


























