package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.User;

import java.util.Set;

public interface UserService {
    public Set<String> findPermissions(String geNumber);
    public Set<String> findRoles(String geNumber);
    public User findByGeNumber(String geNumber);

    //查询员工列表
    public PageInfo<User> pageUser(User user, Integer pageNum, Integer pageSize);
    //添加员工
    public boolean addUser(User user);
    //判断工号是否已存在
    public boolean isNumberExist(String geNumber);
}
