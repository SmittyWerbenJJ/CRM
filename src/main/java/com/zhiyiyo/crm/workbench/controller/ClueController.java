package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.entity.ClueRemark;
import com.zhiyiyo.crm.workbench.entity.Transaction;
import com.zhiyiyo.crm.workbench.exception.ConvertException;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Resource
    private ActivityService activityService;


    @RequestMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/addClue")
    @ResponseBody
    public Map<String, Object> addClue(HttpSession session, Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User) session.getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.addClue(clue));

        return data;
    }

    @RequestMapping("/getCluesByCondition")
    @ResponseBody
    public PaginationVo<Clue> getCluesByCondition(Integer pageNum, Integer pageSize, String fullname, String company,
                                                  String phone, String source, String owner, String mphone, String state) {

        Map<String, Object> condition = new HashMap<>();
        condition.put("start", (pageNum - 1) * pageSize);
        condition.put("pageSize", pageSize);
        condition.put("fullname", fullname);
        condition.put("company", company);
        condition.put("phone", phone);
        condition.put("mphone", mphone);
        condition.put("source", source);
        condition.put("state", state);
        condition.put("owner", owner);

        PaginationVo<Clue> data = new PaginationVo<>();
        data.setDataList(clueService.getCluesByCondition(condition));
        data.setCount(clueService.getClueCountByCondition(condition));

        return data;
    }

    @RequestMapping("/showDetails")
    public ModelAndView showDetails(String id) {
        ModelAndView mv = new ModelAndView("workbench/clue/details");
        mv.addObject("clue", clueService.getClueById(id));
        return mv;
    }

    @PostMapping("/addRemark")
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, ClueRemark remark) {
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateTimeUtil.getSysTime());
        remark.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.addRemark(remark));
        data.put("remark", remark);

        return data;
    }

    /**
     * 通过线索的 id 获取评论列表
     *
     * @param id 线索的 id
     * @return 评论列表
     */
    @RequestMapping("/getRemarksByCId")
    @ResponseBody
    public List<ClueRemark> getRemarksByCId(String id) {
        return clueService.getRemarksByCId(id);
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, ClueRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/deleteRemark")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.deleteRemark(id));

        return data;
    }

    @GetMapping("/getBoundActivities")
    @ResponseBody
    public List<Activity> getBoundActivities(String clueId) {
        return clueService.getBoundActivities(clueId);
    }

    @PostMapping("/unbindActivities")
    @ResponseBody
    public Map<String, Object> unbindActivities(@RequestParam("ids[]") String[] ids) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.unbindActivities(ids));
        return data;
    }

    @GetMapping("/getUnboundActivities")
    @ResponseBody
    public List<Activity> getUnboundActivities(String name, String clueId) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("clueId", clueId);
        return clueService.getUnboundActivities(map);
    }

    @PostMapping("/bindActivities")
    @ResponseBody
    public Map<String, Object> bindActivities(String clueId, @RequestParam("activityIds[]") String[] activityIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", clueService.bindActivities(clueId, activityIds));
        return map;
    }

    @GetMapping("/editClue")
    public ModelAndView editClue(String id) {
        ModelAndView mv = new ModelAndView("workbench/clue/edit");
        mv.addObject("userList", userService.getUserList());
        mv.addObject("clue", clueService.getClueById(id));
        return mv;
    }

    @PostMapping("/updateClue")
    @ResponseBody
    public Map<String, Object> updateClue(HttpSession session, Clue clue) {
        clue.setEditTime(DateTimeUtil.getSysTime());
        clue.setEditBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.updateClue(clue));
        data.put("clue", clue);

        return data;
    }


    @PostMapping("/deleteClues")
    @ResponseBody
    public Map<String, Object> deleteClues(@RequestParam("ids[]") String[] ids) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.deleteClues(ids));
        return data;
    }

    @GetMapping("/getActivities")
    @ResponseBody
    public List<Activity> getActivities(String name) {
        return activityService.getActivitiesByName(name);
    }

    @PostMapping("/convert")
    @ResponseBody
    public Map<String, Object> convert(HttpSession session, String clueId, Boolean createTransaction, Transaction tran) throws ConvertException {
        tran = createTransaction ? tran : null;
        String createBy = ((User) session.getAttribute("user")).getName();

        // 转换线索
        clueService.convert(clueId, createBy, tran);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("msg", "线索转换成功");
        return data;
    }
}































