package com.zhiyiyo.crm.workbench.dao;

import java.util.List;

import com.zhiyiyo.crm.workbench.entity.ActivityRemark;

public interface ActivityRemarkDao {
    /**
     * 删除 id 在 id 列表中的市场活动备注
     *
     * @param ids 备注的 id 列表
     * @return 受影响的行数
     */
    Integer deleteRemarkByActivityIds(String[] ids);

    /**
     * 查询 id 在 id 列表中的备注数目
     *
     * @param ids id 列表
     * @return 备注数目
     */
    Integer queryRemarkCountByIds(String[] ids);


    /**
     * 通过市场活动的 id 获取评价列表
     *
     * @param id 市场活动 id
     * @return 评价列表
     */
    List<ActivityRemark> queryRemarksByAId(String id);

    /**
     * 添加市场活动评价
     * @param remark 评价
     * @return 受影响的行数
     */
    Integer insertRemark(ActivityRemark remark);

    /**
     * 更新市场活动评论
     * @param remark 评论
     * @return 受影响的行数
     */
    Integer updateRemark(ActivityRemark remark);

    /**
     * 删除市场活动备注
     * @param id 备注的 id
     * @return 受影响的行数
     */
    Integer deleteRemark(String id);
}
