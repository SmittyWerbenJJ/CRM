package com.zhiyiyo.crm.listener;

import com.zhiyiyo.crm.settings.entity.DicValue;
import com.zhiyiyo.crm.workbench.service.DicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
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

        Map<String, List<DicValue>> map = dicService.getDict();
        for (Map.Entry<String, List<DicValue>> entry : map.entrySet()) {
            servletContext.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
