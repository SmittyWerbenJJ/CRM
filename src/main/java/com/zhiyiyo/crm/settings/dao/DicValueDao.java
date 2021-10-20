package com.zhiyiyo.crm.settings.dao;

import com.zhiyiyo.crm.settings.entity.DicValue;

import java.util.List;

public interface DicValueDao {
    /**
     * 通过字典类型的主键查找所有的字典值
     * @param code 字典类型的主键
     * @return 字典值列表
     */
    List<DicValue> queryValuesByTypeCode(String code);
}
