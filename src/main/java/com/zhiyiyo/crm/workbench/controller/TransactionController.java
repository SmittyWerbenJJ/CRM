package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.Contacts;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {
    @Resource
    private TransactionService transactionService;

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ContactsService contactsService;

    @GetMapping("/getTransactionsByCondition")
    @ResponseBody
    public PaginationVo<Transaction> getTransactionsByCondition(String owner, String name, String stage,
                                                                String source, String type, String customerName,
                                                                String contactsName, Integer pageNum, Integer pageSize) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("start", (pageNum - 1) * pageSize);
        condition.put("pageSize", pageSize);
        condition.put("name", name);
        condition.put("type", type);
        condition.put("stage", stage);
        condition.put("owner", owner);
        condition.put("source", source);
        condition.put("contactsName", contactsName);
        condition.put("customerName", customerName);

        PaginationVo<Transaction> vo = new PaginationVo<>();
        vo.setCount(transactionService.getTransactionCountByCondition(condition));
        vo.setDataList(transactionService.getTransactionsByCondition(condition));

        return vo;
    }

    @GetMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/getActivitiesByName")
    @ResponseBody
    public List<Activity> getActivitiesByName(String name) {
        return activityService.getActivitiesByName(name);
    }

    @GetMapping("/getContactsByName")
    @ResponseBody
    public List<Contacts> getContactsByName(String name) {
        return contactsService.getContactsByName(name);
    }
}
