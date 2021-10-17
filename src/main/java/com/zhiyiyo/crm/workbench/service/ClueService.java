package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.workbench.entity.Clue;

public interface ClueService {
    /**
     * 添加一条线索
     * @param clue 线索
     * @return 添加是否成功
     */
    boolean addClue(Clue clue);
}
