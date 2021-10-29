package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {
    /**
     * 删除指定的行
     * @param id 行 id
     * @return 受影响的行数
     */
    Integer deleteById(String id);

    /**
     * 插入一条关联信息
     * @param relation 关联关系
     * @return 受影响的行数
     */
    Integer insertRelation(ClueActivityRelation relation);
}
