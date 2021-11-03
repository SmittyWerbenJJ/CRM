package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Resource
    private CustomerService customerService;

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
    public Map<String, Object> addContacts(HttpSession session, Contacts contacts, String customerName) {
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(((User) session.getAttribute("user")).getName());
        contacts.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.addContacts(contacts, customerName));

        return data;
    }

    @RequestMapping("/showDetails")
    public ModelAndView showDetails(String id) {
        ModelAndView mv = new ModelAndView("workbench/contacts/details");
        mv.addObject("contacts", contactsService.getContactsById(id));
        return mv;
    }

    @GetMapping("/getRemarksByCId")
    @ResponseBody
    public List<ContactsRemark> getRemarksByCId(String id){
        return contactsService.getRemarksByCId(id);
    }

    @PostMapping("/addRemark")
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, ContactsRemark remark){
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateTimeUtil.getSysTime());
        remark.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.addRemark(remark));
        data.put("remark", remark);
        return data;
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, ContactsRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/deleteRemark")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.deleteRemark(id));

        return data;
    }

    @GetMapping("/getBoundActivities")
    @ResponseBody
    public List<Activity> getBoundActivities(String contactsId) {
        return contactsService.getBoundActivities(contactsId);
    }

    @GetMapping("/getUnboundActivities")
    @ResponseBody
    public List<Activity> getUnboundActivities(String name, String contactsId) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("contactsId", contactsId);
        return contactsService.getUnboundActivities(map);
    }

    @PostMapping("/bindActivities")
    @ResponseBody
    public Map<String, Object> bindActivities(String contactsId, @RequestParam("activityIds[]") String[] activityIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", contactsService.bindActivities(contactsId, activityIds));
        return map;
    }

    @PostMapping("/unbindActivities")
    @ResponseBody
    public Map<String, Object> unbindActivities(@RequestParam("ids[]") String[] ids) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.unbindActivities(ids));
        return data;
    }

    @GetMapping("/getBoundTransactions")
    @ResponseBody
    public List<Transaction> getBoundTransactions(String contactsId){
        return contactsService.getBoundTransactions(contactsId);
    }

    @GetMapping("/getCustomersLikeName")
    @ResponseBody
    public List<Customer> getCustomersLikeName(@RequestParam("query") String name) {
        return customerService.getCustomersLikeName(name);
    }

    @GetMapping("/editContacts")
    public ModelAndView editContacts(String id){
        ModelAndView mv= new ModelAndView("workbench/contacts/edit");
        mv.addObject("userList", userService.getUserList());
        mv.addObject("contacts", contactsService.getContactsById(id));
        return mv;
    }

    @PostMapping("/updateContacts")
    @ResponseBody
    public Map<String, Object> updateContacts(HttpSession session, Contacts contacts, String customerName){
        contacts.setEditBy(((User) session.getAttribute("user")).getName());
        contacts.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.updateContacts(contacts, customerName));
        data.put("contacts", contacts);

        return data;
    }

    @PostMapping("/deleteContacts")
    @ResponseBody
    public Map<String, Object> deleteContacts(@RequestParam("ids[]") String[] ids){
        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.deleteContacts(ids));
        return data;
    }
}





























