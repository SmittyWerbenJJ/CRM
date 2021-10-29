package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/workbench/contacts")
public class ContactsController {
    @Resource
    private ContactsService contactsService;

    @GetMapping("/getContactsByCondition")
    @ResponseBody
    public PaginationVo<Contacts> getContactsByCondition(String owner, String fullname, String birth, String customerName, String source, Integer pageNum, Integer pageSize) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("start", (pageNum - 1) * pageSize);
        condition.put("pageSize", pageSize);
        condition.put("owner", owner);
        condition.put("fullname", fullname);
        condition.put("customerName", customerName);
        condition.put("source", source);
        condition.put("birth", birth);

        PaginationVo<Contacts> vo = new PaginationVo<>();
        vo.setCount(contactsService.getContactsCountByCondition(condition));
        vo.setDataList(contactsService.getContactsByCondition(condition));

        return vo;
    }
}
