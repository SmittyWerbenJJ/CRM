package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.*;
import com.zhiyiyo.crm.workbench.exception.CustomerException;
import com.zhiyiyo.crm.workbench.service.ContactsService;
import com.zhiyiyo.crm.workbench.service.CustomerService;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @Resource
    private ContactsService contactsService;

    @Resource
    private UserService userService;

    @Resource
    private TransactionService transactionService;

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

    @RequestMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/addCustomer")
    @ResponseBody
    public Map<String, Object> addCustomer(HttpSession session, Customer customer){
        customer.setId(UUIDUtil.getUUID());
        customer.setCreateBy(((User) session.getAttribute("user")).getName());
        customer.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.addCustomer(customer));

        return data;
    }

    @GetMapping("/getUserListAndCustomer")
    @ResponseBody
    public Map<String, Object> getUserListAndCustomer(String id) {
        List<User> userList = userService.getUserList();
        Customer customer = customerService.getCustomerById(id);

        Map<String, Object> data = new HashMap<>();
        data.put("userList", userList);
        data.put("customer", customer);

        return data;
    }


    @PostMapping("/updateCustomer")
    @ResponseBody
    public Map<String, Object> updateCustomer(HttpSession session, Customer customer) {
        customer.setEditTime(DateTimeUtil.getSysTime());
        customer.setEditBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.updateCustomer(customer));
        data.put("customer", customer);

        return data;
    }

    @RequestMapping("/showDetails")
    public ModelAndView showDetails(String id) {
        ModelAndView mv = new ModelAndView("workbench/customer/details");
        mv.addObject("customer", customerService.getCustomer(id));
        return mv;
    }

    @GetMapping("/getRemarksByCId")
    @ResponseBody
    public List<CustomerRemark> getRemarksByCId(String id){
        return customerService.getRemarksByCId(id);
    }

    @PostMapping("/addRemark")
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, CustomerRemark remark){
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateTimeUtil.getSysTime());
        remark.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.addRemark(remark));
        data.put("remark", remark);
        return data;
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, CustomerRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/deleteRemark")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.deleteRemark(id));

        return data;
    }

    @GetMapping("/getBoundTransactions")
    @ResponseBody
    public List<Transaction> getBoundTransactions(String customerId){
        return customerService.getBoundTransactions(customerId);
    }

    @PostMapping("/addContacts")
    @ResponseBody
    public Map<String, Object> addContacts(HttpSession session, Contacts contacts) {
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(((User) session.getAttribute("user")).getName());
        contacts.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", contactsService.addContacts(contacts));
        data.put("contacts", contacts);
        return data;
    }

    @GetMapping("/getBoundContacts")
    @ResponseBody
    public List<Contacts> getBoundContacts(String customerId){
        return contactsService.getContactsByCustomerId(customerId);
    }

    @PostMapping("/deleteCustomers")
    @ResponseBody
    public Map<String, Object> deleteCustomers(@RequestParam("ids[]") String[] ids) throws CustomerException {
        Map<String, Object> data = new HashMap<>();
        data.put("success", customerService.deleteCustomers(ids));
        return data;
    }

    @PostMapping("/deleteTransactions")
    @ResponseBody
    public Map<String, Object> deleteTransactions(@RequestParam("ids[]") String[] ids){
        Map<String, Object> data = new HashMap<>();
        data.put("success", transactionService.deleteTransactions(ids));
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






























