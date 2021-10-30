package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.entity.ActivityRemark;
import com.zhiyiyo.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("workbench/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @Resource
    private UserService userService;

    /**
     * 添加市场活动
     *
     * @param session  会话
     * @param activity 市场活动
     * @return 插入是否成功 json 数据
     */
    @PostMapping("/addActivity")
    @ResponseBody
    public Map<String, Object> addActivity(HttpSession session, Activity activity) {
        // 更新市场活动的值
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(((User) session.getAttribute("user")).getName());
        activity.setCreateTime(DateTimeUtil.getSysTime());

        // 将市场活动插入数据库
        boolean success = activityService.addActivity(activity);
        Map<String, Object> data = new HashMap<>();
        data.put("success", success);

        return data;
    }

    @GetMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/getActivitiesByCondition")
    @ResponseBody
    public PaginationVo<Activity> getActivitiesByCondition(Integer pageNum, Integer pageSize, String name, String owner,
            String startDate, String endDate) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("start", (pageNum - 1) * pageSize);
        condition.put("pageSize", pageSize);
        condition.put("name", name);
        condition.put("owner", owner);
        condition.put("startDate", startDate);
        condition.put("endDate", endDate);
        return activityService.getActivitiesByCondition(condition);
    }

    @PostMapping("/deleteActivities")
    @ResponseBody
    public Map<String, Object> deleteActivities(@RequestParam("ids[]") String[] ids) {
        boolean success = activityService.deleteActivity(ids);

        Map<String, Object> data = new HashMap<>();
        data.put("success", success);

        return data;
    }

    @GetMapping("/getUserListAndActivity")
    @ResponseBody
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> userList = userService.getUserList();
        Activity activity = activityService.getActivity(id);

        Map<String, Object> data = new HashMap<>();
        data.put("userList", userList);
        data.put("activity", activity);

        return data;
    }

    @PostMapping("/updateActivity")
    @ResponseBody
    public Map<String, Object> updateActivity(HttpSession session, Activity activity) {
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User) session.getAttribute("user")).getName());

        boolean success = activityService.updateActivity(activity);

        Map<String, Object> data = new HashMap<>();
        data.put("success", success);
        data.put("activity", activity);
        return data;
    }

    @GetMapping("/showDetails")
    public ModelAndView showDetails(String id) {
        ModelAndView mv = new ModelAndView("/workbench/activity/details");
        mv.addObject("activity", activityService.getActivityById(id));
        return mv;
    }

    /**
     * 通过市场活动的 id 获取评论列表
     *
     * @param id 市场活动的 id
     * @return 评论列表
     */
    @GetMapping("/getRemarksByAId")
    @ResponseBody
    public List<ActivityRemark> getRemarksByAId(String id) {
        return activityService.getRemarksByAId(id);
    }

    @PostMapping("/addRemark")
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, ActivityRemark remark) {
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateTimeUtil.getSysTime());
        remark.setCreateBy(((User) session.getAttribute("user")).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("success", activityService.addRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, ActivityRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", activityService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }

    @PostMapping("/deleteRemark")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", activityService.deleteRemark(id));

        return data;
    }
}
