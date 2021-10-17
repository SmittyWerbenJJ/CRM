package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Clue;

public interface ClueDao {
    /**
     * 添加一条线索
     * @param clue 线索
     * @return 受影响的行数
     */
    Integer insertClue(Clue clue);
}
