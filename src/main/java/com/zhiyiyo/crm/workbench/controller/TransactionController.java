package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {
    @Resource
    private TransactionService transactionService;

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
}
