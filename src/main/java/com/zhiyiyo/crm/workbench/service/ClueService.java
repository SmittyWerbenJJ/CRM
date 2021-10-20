package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.entity.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueService {
    /**
     * 添加一条线索
     * @param clue 线索
     * @return 添加是否成功
     */
    boolean addClue(Clue clue);

    /**
     * 根据条件查询线索
     * @param condition 查询条件
     * @return 符合查询条件的线索列表
     */
    List<Clue> getCluesByCondition(Map<String, Object> condition);

    /**
     * 根据条件查询线索的总数量
     * @param condition 查询条件
     * @return 符合查询条件的线索列表
     */
    Integer getClueCountByCondition(Map<String, Object> condition);

    /**
     * 根据线索的 id 查找线索
     * @param id 线索的 id
     * @return 查找到的线索
     */
    Clue getClueById(String id);

    /**
     * 增加一条线索评论
     * @param remark 评论
     * @return 添加是否成功
     */
    boolean addRemark(ClueRemark remark);

    /**
     * 通过线索的 id 获取评论列表
     * @param id 线索 id
     * @return 评论列表
     */
    List<ClueRemark> getRemarksByCId(String id);

    /**
     * 更新线索评论
     * @param remark 评论
     * @return 更新是否成功
     */
    boolean updateRemark(ClueRemark remark);

    /**
     * 删除一条线索的备注
     * @param id 备注的 id
     * @return 删除是否成功
     */
    boolean deleteRemark(String id);
}
