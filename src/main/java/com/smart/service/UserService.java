
package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;
import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> findPermissions(String var1);

    Set<String> findRoles(String var1);

    User findByGeNumber(String var1);

    List<User> getUserList();

    List<Record> getRecordByTime(String var1, String var2, String var3);

    PageInfo<User> pageUser(User var1, Integer var2, Integer var3);

    boolean addUser(User var1);

    boolean isNumberExist(String var1);

    boolean updateUser(User var1);

    boolean delUser(String var1);

    boolean updatePassword(String var1, String var2, String var3);

    boolean lockUser(int[] var1);

    boolean clearUser(int[] var1);

    PageInfo<Inform> pageInform(Inform var1, Integer var2, Integer var3);

    Inform getInformById(int var1);

    boolean delInform(int var1);

    User getUserByGrNumber(String var1);
}
