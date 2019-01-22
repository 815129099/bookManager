package com.smart.dao;

import com.smart.bean.Inform;
import com.smart.bean.MoneyRecord;
import com.smart.bean.Record;
import com.smart.bean.User;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
     Set<String> findRoles(String var1);

     Set<String> findPermissions(String var1);

     User findByGeNumber(String var1);

     List<User> listUser(User var1);

     List<User> getUserList();

     List<Record> getRecordByTime(@Param("begin") String var1, @Param("end") String var2, @Param("userState") String var3);

     void addUser(User var1);

     User getUserById(String var1);

     void updateUser(User var1);

     void delUser(@Param("geNumber") String var1, @Param("updateTime") String var2);

     void updatePassword(@Param("password") String var1, @Param("geNumber") String var2, @Param("updateTime") String var3);

     void lockUser(@Param("id") int var1, @Param("updateTime") String var2);

     void clearUser(@Param("id") int var1, @Param("updateTime") String var2);

     List<Inform> listInform(Inform var1);

     Inform getInformById(int var1);

     boolean addInform(Inform var1);

     void deleteInform(@Param("id") int var1, @Param("updateTime") String var2);

     void deductMoneyRecord(MoneyRecord var1);

     void deductMoney(User var1);
}
