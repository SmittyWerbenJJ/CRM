package com.zhiyiyo.crm.workbench.dao;

public interface ActivityRemarkDao {
    /**
     * 删除 id 在 id 列表中的市场活动备注
     *
     * @param ids 备注的 id 列表
     * @return 受影响的行数
     */
    Integer deleteRemarkByIds(String[] ids);

    /**
     * 查询 id 在 id 列表中的备注数目
     *
     * @param ids id 列表
     * @return 备注数目
     */
    Integer queryRemarkCountByIds(String[] ids);
}
