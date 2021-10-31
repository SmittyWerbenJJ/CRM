package com.zhiyiyo.crm.workbench.dao;

import com.zhiyiyo.crm.workbench.entity.CustomerRemark;

import java.util.List;

public interface CustomerRemarkDao {

    /**
     * 插入多条评论
     * @param remarks 客户评论
     * @return 受影响的行数
     */
    Integer insertRemarks(List<CustomerRemark> remarks);
}
