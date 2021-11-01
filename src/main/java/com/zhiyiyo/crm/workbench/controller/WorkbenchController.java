package com.zhiyiyo.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workbench")
public class WorkbenchController {

    @RequestMapping("/index.html")
    public String index() {
        return "workbench/index";
    }

    @RequestMapping("/activity/index.html")
    public String activityIndex() {
        return "workbench/activity/index";
    }

    @RequestMapping("/clue/index.html")
    public String clueIndex() {
        return "workbench/clue/index";
    }

    @RequestMapping("/clue/add.html")
    public String clueAdd() {
        return "workbench/clue/add";
    }

    @RequestMapping("/clue/convert.html")
    public String clueConvert() {
        return "workbench/clue/convert";
    }

    @RequestMapping("/contacts/index.html")
    public String contactsIndex() {
        return "workbench/contacts/index";
    }

    @RequestMapping("/contacts/add.html")
    public String contactsAdd(){
        return "workbench/contacts/add";
    }

    @RequestMapping("/customer/index.html")
    public String customerIndex(){
        return "workbench/customer/index";
    }

    @RequestMapping("/transaction/index.html")
    public String transactionIndex(){
        return "workbench/transaction/index";
    }

    @RequestMapping("/transaction/add.html")
    public String transactionAdd(){
        return "workbench/transaction/add";
    }
}
