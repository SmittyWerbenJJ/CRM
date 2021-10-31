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
}
