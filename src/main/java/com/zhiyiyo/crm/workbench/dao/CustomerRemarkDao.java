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

    /**
     * 通过客户 id 查询评论
     * @param id 客户 id
     * @return 评论列表
     */
    List<CustomerRemark> queryRemarksByCId(String id);

    /**
     * 插入一条评论
     * @param remark 客户评论
     * @return 受影响的行数
     */
    Integer insert(CustomerRemark remark);

    Integer update(CustomerRemark remark);

    Integer delete(String id);

    Integer queryCountByCustomerIds(String[] ids);

    Integer deleteByCustomerIds(String[] ids);
}
