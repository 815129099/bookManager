package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.MoneyRecord;
import com.smart.dao.UtilDao;
import com.smart.redis.DateUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("utilService")
public class UtilServiceImpl implements UtilService {
    @Autowired
    private UtilDao utilDao;

    public UtilServiceImpl() {
    }

    public PageInfo<AccessRecord> pageAccess(AccessRecord accessRecord, Integer pageNum, Integer pageSize) {
        System.out.println(accessRecord.getGeNumber() + "," + accessRecord.getIpNumber());
        PageInfo<AccessRecord> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<AccessRecord> aList = this.utilDao.listAccess(accessRecord);
        page = new PageInfo(aList);
        return page;
    }

    public void addAccess(AccessRecord accessRecord) {
        AccessRecord accessRecord1 = this.utilDao.getAccessById(accessRecord.getSessionId());
        if (StringUtils.isEmpty(accessRecord1)) {
            accessRecord.setBeginTime(DateUtil.getDate());
            this.utilDao.addAccess(accessRecord);
        }

    }

    public PageInfo<MoneyRecord> pageMoney(MoneyRecord accessRecord, Integer pageNum, Integer pageSize) {
        PageInfo<MoneyRecord> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<MoneyRecord> aList = this.utilDao.listMoney(accessRecord);
        page = new PageInfo(aList);
        return page;
    }
}
