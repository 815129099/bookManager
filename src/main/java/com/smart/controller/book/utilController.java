
package com.smart.controller.book;

import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.MoneyRecord;
import com.smart.service.UtilService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/"})
public class utilController {
    @Autowired
    private UtilService utilService;

    public utilController() {
    }

    @RequestMapping({"/ipList.do"})
    public ModelAndView userList() {
        return new ModelAndView("book/ipList");
    }

    @RequestMapping({"/usbTest.do"})
    public ModelAndView usbTest() {
        return new ModelAndView("usbTest");
    }

    @RequestMapping({"/listAccess.do"})
    @ResponseBody
    public Map<String, Object> listAccess(AccessRecord accessRecord, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (accessRecord.getIpNumber() != null) {
            accessRecord.setIpNumber("%" + accessRecord.getIpNumber() + "%");
        }

        if (accessRecord.getGeNumber() != null) {
            accessRecord.setGeNumber("%" + accessRecord.getGeNumber() + "%");
        }

        PageInfo<AccessRecord> page = this.utilService.pageAccess(accessRecord, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping({"/accountList.do"})
    public ModelAndView accountList() {
        return new ModelAndView("book/accountList");
    }

    @RequestMapping({"/listMoney.do"})
    @ResponseBody
    public Map<String, Object> listMoney(MoneyRecord moneyRecord, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (moneyRecord.getGeNumber() != null) {
            moneyRecord.setGeNumber("%" + moneyRecord.getGeNumber() + "%");
        }

        PageInfo<MoneyRecord> page = this.utilService.pageMoney(moneyRecord, pageNum, pageSize);
        map.put("page", page);
        return map;
    }
}
