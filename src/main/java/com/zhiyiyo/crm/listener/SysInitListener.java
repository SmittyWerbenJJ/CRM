package com.zhiyiyo.crm.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiyiyo.crm.settings.entity.DicValue;
import com.zhiyiyo.crm.settings.service.DicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysInitListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private DicService dicService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) event.getApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();

        // 保存数据字典
        Map<String, List<DicValue>> map = dicService.getDict();
        for (Map.Entry<String, List<DicValue>> entry : map.entrySet()) {
            servletContext.setAttribute(entry.getKey(), entry.getValue());
        }

        // 保存阶段-可能性字典
        try {
            File file = ResourceUtils.getFile("classpath:static/data/stage_possibility.json");
            Map<String, Integer> stagePossibilityMap = new ObjectMapper().readValue(file, HashMap.class);
            servletContext.setAttribute("stagePossibilityMap", stagePossibilityMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
