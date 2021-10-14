package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Activity;
import com.zhiyiyo.crm.workbench.service.ActivityService;
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
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
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

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping("/getActivities.do")
    @ResponseBody
    public PaginationVo<Activity> getActivities(HttpSession session, Integer pageNum, Integer pageSize, String name, String owner,
            String startDate, String endDate) {
        return activityService.getActivities(pageNum, pageSize, name, owner, startDate, endDate);
    }

}
