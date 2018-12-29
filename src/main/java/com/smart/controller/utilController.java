package com.smart.controller;

import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.redis.ExcelParam;
import com.smart.redis.ExcelUtil;
import com.smart.service.SystemLogoutFilter;
import com.smart.service.UtilService;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class utilController {
    @Autowired
    private UtilService utilService;

    @RequestMapping(value="/ipList.do")
    public ModelAndView userList() {
        return new ModelAndView("ipList");
    }

    //查询用户列表
    @RequestMapping(value = "/listAccess.do")
    @ResponseBody
    public Map<String,Object> listAccess(AccessRecord accessRecord, Integer pageNum, Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        //获取分页信息
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (accessRecord.getIpNumber() != null) {
            accessRecord.setIpNumber("%" + accessRecord.getIpNumber()+ "%");
        }
        if(accessRecord.getGeNumber() != null){
            accessRecord.setGeNumber("%" + accessRecord.getGeNumber() + "%");
        }
        PageInfo<AccessRecord> page = utilService.pageAccess(accessRecord, pageNum, pageSize);
        map.put("page", page);
        return map;
    }


}
