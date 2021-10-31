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

    /**
     * 插入多条关联信息
     * @param relations 关系
     * @return 受影响的行数
     */
    Integer insertRelations(List<ClueActivityRelation> relations);

    /**
     * 根据线索 id 删除行
     * @param ids 线索 id 列表
     * @return 受影响的行数
     */
    Integer deleteByClueIds(String[] ids);

    /**
     * 根据线索 id 删除行
     * @param id 线索 id
     * @return 受影响的行数
     */
    Integer deleteByClueId(String id);

    /**
     * 根据线索 id 统计记录数量
     * @param ids 线索 id 列表
     * @return 总记录数
     */
    Integer queryCountByClueIds(String[] ids);

    /**
     * 通过线索 id 查询记录
     * @param id 线索 id
     * @return 关联记录列表
     */
    List<ClueActivityRelation> queryRelationsByClueId(String id);
}
