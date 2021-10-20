package com.zhiyiyo.crm.workbench.controller;

import com.zhiyiyo.crm.settings.entity.User;
import com.zhiyiyo.crm.settings.service.UserService;
import com.zhiyiyo.crm.utils.DateTimeUtil;
import com.zhiyiyo.crm.utils.UUIDUtil;
import com.zhiyiyo.crm.vo.PaginationVo;
import com.zhiyiyo.crm.workbench.entity.Clue;
import com.zhiyiyo.crm.workbench.entity.ClueRemark;
import com.zhiyiyo.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("getUserList.do")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping(value = "/addClue.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addClue(HttpSession session, Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User) session.getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.addClue(clue));

        return data;
    }

    @RequestMapping("getCluesByCondition.do")
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

    @RequestMapping("/showDetails.do")
    public ModelAndView showDetails(String id) {
        ModelAndView mv = new ModelAndView("/workbench/clue/details.jsp");
        mv.addObject("clue", clueService.getClueById(id));
        return mv;
    }

    @RequestMapping(value = "/addRemark.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRemark(HttpSession session, ClueRemark remark){
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
    @RequestMapping("/getRemarksByCId.do")
    @ResponseBody
    public List<ClueRemark> getRemarksByCId(String id) {
        return clueService.getRemarksByCId(id);
    }


    @RequestMapping(value = "/updateRemark.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateRemark(HttpSession session, ClueRemark remark) {
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditTime(DateTimeUtil.getSysTime());

        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.updateRemark(remark));
        data.put("remark", remark);

        return data;
    }


    @RequestMapping(value = "/deleteRemark.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", clueService.deleteRemark(id));

        return data;
    }
}
