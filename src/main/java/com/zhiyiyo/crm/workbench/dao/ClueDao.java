package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    /**
     * 添加一条线索
     * @param clue 线索
     * @return 受影响的行数
     */
    Integer insertClue(Clue clue);

    /**
     * 根据条件查询线索
     * @param condition 条件字典
     * @return 线索列表
     */
    List<Clue> queryCluesByCondition(Map<String, Object> condition);

    /**
     * 根据条件查询线索
     * @param condition 条件字典
     * @return 线索列表
     */
    Integer queryClueCountByCondition(Map<String, Object> condition);

    /**
     * 根据线索的 id 查找线索
     * @param id 线索的 id
     * @return 找到的线索
     */
    Clue queryClueById(String id);

    /**
     * 更新线索
     * @param clue 线索
     * @return 受影响的行数
     */
    Integer updateClue(Clue clue);

    /**
     * 删除多条线索
     * @param ids 线索 id 列表
     * @return 受影响的行数
     */
    Integer deleteClues(String[] ids);
}
