
package com.smart.dao;

import com.smart.bean.AccessRecord;
import com.smart.bean.MoneyRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UtilDao {
     List<AccessRecord> listAccess(AccessRecord var1);

     void addAccess(AccessRecord var1);

     void logoutTime(AccessRecord var1);

     AccessRecord getAccessById(@Param("sessionId") String var1);

     List<MoneyRecord> listMoney(MoneyRecord var1);
}
