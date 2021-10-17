package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private ClueService clueService;

    @Resource
    private UserService userService;

    @RequestMapping("getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping(value = "/addClue.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addClue(HttpSession session, Clue clue) {
        clue.setCreateBy(((User) session.getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.addClue(clue));

        return data;
    }
}
