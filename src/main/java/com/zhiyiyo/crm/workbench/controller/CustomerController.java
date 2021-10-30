package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.entity.Customer;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @GetMapping("/getCustomersByCondition")
    @ResponseBody
    public PaginationVo<Customer> getCustomersByCondition(String owner, String name, String phone, String website, Integer pageNum, Integer pageSize) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("start", (pageNum - 1) * pageSize);
        condition.put("pageSize", pageSize);
        condition.put("owner", owner);
        condition.put("name", name);
        condition.put("website", website);
        condition.put("phone", phone);

        PaginationVo<Customer> vo = new PaginationVo<>();
        vo.setCount(customerService.getCustomerCountByCondition(condition));
        vo.setDataList(customerService.getCustomerByCondition(condition));

        return vo;
    }
}
