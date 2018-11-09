package com.smart.dao;

import com.smart.bean.Book;
import com.smart.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserDao {
     Set<String> findRoles(String geNumber);
     Set<String> findPermissions(String geNumber);
     User findByGeNumber(String geNumber);

     //用户列表
     public List<User> listUser(User user);
     //添加用户
     public void addUser(User user);
     //通过Id获取用户
     public User getUserById(String geNumber);
     //修改
     public void updateUser(User user);
     //删除
     public void delUser(@Param("geNumber")String geNumber,@Param("updateTime") String updateTime);

}
