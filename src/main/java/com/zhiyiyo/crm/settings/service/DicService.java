package com.zhiyiyo.crm.settings.service;

import com.zhiyiyo.crm.settings.entity.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getDict();
}
