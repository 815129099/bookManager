package com.smart.dao;

import com.smart.bean.Book;
import com.smart.bean.User;

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
}
