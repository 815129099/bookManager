package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.UserDao;
import com.smart.dao.UtilDao;
import com.smart.redis.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service("utilService")
public class UtilServiceImpl implements UtilService {

    @Autowired
    private UtilDao utilDao;

    @Override
    public PageInfo<AccessRecord> pageAccess(AccessRecord accessRecord, Integer pageNum, Integer pageSize) {
        System.out.println(accessRecord.getGeNumber()+","+accessRecord.getIpNumber());
        PageInfo<AccessRecord> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<AccessRecord> aList = utilDao.listAccess(accessRecord);
        page = new PageInfo<AccessRecord>(aList);
        return page;
    }

    @Override
    public void addAccess(AccessRecord accessRecord) {
        AccessRecord accessRecord1 = utilDao.getAccessById(accessRecord.getSessionId());
        if(StringUtils.isEmpty(accessRecord1)){
            accessRecord.setBeginTime(DateUtil.getDate());
            utilDao.addAccess(accessRecord);
        }
    }
}
