package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Resource
    private CustomerService customerService;

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

    @GetMapping("/getCustomersLikeName")
    @ResponseBody
    public List<Customer> getCustomersLikeName(@RequestParam("query") String name) {
        List<Customer> list = customerService.getCustomersLikeName(name);
        return list;
    }

    @PostMapping("/addTransaction")
    @ResponseBody
    public Map<String, Object> addTransaction(HttpSession session, Transaction tran, String customerName) {
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.addTransaction(tran, customerName));
        data.put("transaction", tran);

        return data;
    }

    @GetMapping("/showDetails")
    public ModelAndView showDetails(HttpSession session, String id) {
        ModelAndView mv = new ModelAndView("/workbench/transaction/details");

        // 获取交易
        Transaction tran = transactionService.getTransactionById(id);
        mv.addObject("transaction", tran);

        // 设置可能性
        Map<String, Integer> map = (Map<String, Integer>) session.getServletContext().getAttribute("stagePossibilityMap");
        mv.addObject("possibility", map.get(tran.getStage()));

        return mv;
    }

    @PostMapping("/addRemark")
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, TransactionRemark remark) {
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateTimeUtil.getSysTime());
        remark.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.addRemark(remark));
        data.put("remark", remark);
        return data;
    }

    @GetMapping("/getRemarksByTransactionId")
    @ResponseBody
    public List<TransactionRemark> getRemarksByTransactionId(String id) {
        return transactionService.getRemarksByTransactionId(id);
    }

    @GetMapping("/getStagePossibilityMap")
    @ResponseBody
    public Map<String, Integer> getStagePossibilityMap(HttpSession session) {
        return (Map<String, Integer>) session.getServletContext().getAttribute("stagePossibilityMap");
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, TransactionRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/deleteRemark")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.deleteRemark(id));
        return data;
    }

    @GetMapping("/getHistories")
    @ResponseBody
    public List<TransactionHistory> getHistories(String transactionId) {
        return transactionService.getHistories(transactionId);
    }

    @PostMapping("/updateStage")
    @ResponseBody
    public Map<String, Object> updateStage(HttpSession session, String id, String stage) {
        Transaction tran = transactionService.getTransactionById(id);
        tran.setStage(stage);
        tran.setEditBy(((User) session.getAttribute("user")).getName());
        tran.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.updateStage(tran));
        //data.put("stagePossibilityMap", session.getServletContext().getAttribute("stagePossibilityMap"));
        data.put("transaction", tran);

        return data;
    }
}




























