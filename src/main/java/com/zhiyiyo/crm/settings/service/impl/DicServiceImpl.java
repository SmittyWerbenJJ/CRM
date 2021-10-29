package com.zhiyiyo.crm.settings.service.impl;

import com.zhiyiyo.crm.settings.dao.DicTypeDao;
import com.zhiyiyo.crm.settings.dao.DicValueDao;
import com.zhiyiyo.crm.settings.entity.DicValue;
import com.zhiyiyo.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;

    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getDict() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<String> dicTypeCodes = dicTypeDao.queryTypeCodes();
        for (String code : dicTypeCodes) {
            map.put(code, dicValueDao.queryValuesByTypeCode(code));
        }

        return map;
    }
}
