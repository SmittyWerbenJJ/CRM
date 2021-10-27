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
}
