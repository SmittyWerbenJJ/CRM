package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    /**
     * 插入一条线索评论
     * @param remark 评论
     * @return 受影响的行数
     */
    Integer insertRemark(ClueRemark remark);

    /**
     * 线索的 id 获取评论列表
     *
     * @param id 线索 id
     * @return 评论列表
     */
    List<ClueRemark> queryRemarksByCId(String id);

    /**
     * 更新线索评论
     * @param remark 评论
     * @return 受影响的行数
     */
    Integer updateRemark(ClueRemark remark);

    /**
     * 删除线索的备注
     * @param id 备注的 id
     * @return 受影响的行数
     */
    Integer deleteRemark(String id);
}
