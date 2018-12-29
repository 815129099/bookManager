package com.smart.dao;

import com.smart.bean.AccessRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UtilDao {
     public List<AccessRecord> listAccess(AccessRecord accessRecord);
     public void addAccess(AccessRecord accessRecord);
     public void logoutTime(AccessRecord accessRecord);
     public AccessRecord getAccessById(@Param("sessionId") String sessionId);
}
