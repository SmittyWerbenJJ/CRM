package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationDao {

    /**
     * 插入多条关联关系
     * @param relations 关联关系列表
     * @return 受影响的行数
     */
    Integer insertRelations(List<ContactsActivityRelation> relations);

    /**
     * 通过主键 id 删除关联关系
     * @param ids 主键 id 列表
     * @return 受影响的行数
     */
    Integer deleteByIds(String[] ids);

    /**
     * 通过市场活动 id 删除关联关系
     * @param ids 市场活动 id 列表
     * @return 受影响的行数
     */
    Integer deleteByActivityIds(String[] ids);

    /**
     * 通过市场活动 id 查询关联数
     * @param ids 市场活动 id 列表
     * @return 行数
     */
    Integer queryCountByActivityIds(String[] ids);
}
