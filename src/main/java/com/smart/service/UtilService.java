
package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.MoneyRecord;

public interface UtilService {
    PageInfo<AccessRecord> pageAccess(AccessRecord var1, Integer var2, Integer var3);

    void addAccess(AccessRecord var1);

    PageInfo<MoneyRecord> pageMoney(MoneyRecord var1, Integer var2, Integer var3);
}
