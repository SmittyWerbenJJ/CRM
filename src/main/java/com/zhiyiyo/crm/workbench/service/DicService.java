package com.zhiyiyo.crm.workbench.service;

import com.zhiyiyo.crm.settings.dao.DicTypeDao;
import com.zhiyiyo.crm.settings.entity.DicValue;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getDict();
}
