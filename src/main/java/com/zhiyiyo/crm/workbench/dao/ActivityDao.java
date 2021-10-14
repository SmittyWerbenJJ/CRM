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
    int insert(Activity activity);

    /**
     * 更新市场活动信息
     *
     * @param activity 市场活动
     * @return 受影响的行数
     */
    int update(Activity activity);

    /**
     * 删除多条市场活动记录
     *
     * @param idList 市场活动 id 列表
     * @return 受影响的行数
     */
    int delete(List<String> idList);

    /**
     * 查询符合匹配条件的市场活动总数
     *
     * @return 总条数
     */
    Integer queryActivityCount(Map<String,Object> map);

    /**
     * 查询符合匹配条件的市场活动
     *
     * @return 符合匹配条件的分页查询结果
     */
    List<Activity> queryActivities(Map<String, Object> map);
}
