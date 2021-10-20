package com.zhiyiyo.crm.listener;

import com.zhiyiyo.crm.settings.entity.DicValue;
import com.zhiyiyo.crm.workbench.service.DicService;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysInitListener extends ContextLoaderListener {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        WebApplicationContext applicationContext = super.initWebApplicationContext(servletContext);

        // 设置数据字典
        DicService service = (DicService) applicationContext.getBean("dicServiceImpl");
        Map<String, List<DicValue>> map = service.getDict();
        for (Map.Entry<String, List<DicValue>> entry : map.entrySet()) {
            servletContext.setAttribute(entry.getKey(), entry.getValue());
        }

        return applicationContext;
    }
}
