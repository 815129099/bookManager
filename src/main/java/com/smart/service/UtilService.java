package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;

import java.util.List;
import java.util.Set;

public interface UtilService {

    //查询访问列表
    public PageInfo<AccessRecord> pageAccess(AccessRecord accessRecord, Integer pageNum, Integer pageSize);
    //增加访问记录
    public void addAccess(AccessRecord accessRecord);
}
