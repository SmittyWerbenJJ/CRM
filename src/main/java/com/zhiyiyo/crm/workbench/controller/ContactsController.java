package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/contacts")
public class ContactsController {
    @Resource
    private ContactsService contactsService;

    @Resource
    private UserService userService;

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

    @GetMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/addContacts")
    @ResponseBody
    public Map<String, Object> addContacts(HttpSession session, Contacts contacts){
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(((User) session.getAttribute("user")).getName());
        contacts.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.addContacts(contacts));

        return data;
    }
}





























