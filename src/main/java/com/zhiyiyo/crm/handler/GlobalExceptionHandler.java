package com.zhiyiyo.crm.handler;

import com.zhiyiyo.crm.settings.exception.LoginException;
import com.zhiyiyo.crm.settings.exception.SignupException;
import com.zhiyiyo.crm.workbench.exception.ActivityException;
import com.zhiyiyo.crm.workbench.exception.ConvertException;
import com.zhiyiyo.crm.workbench.exception.CustomerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LoginException.class, SignupException.class, ConvertException.class,
                        ActivityException.class, CustomerException.class})
    @ResponseBody
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        data.put("msg", e.getMessage());
        return data;
    }

}
