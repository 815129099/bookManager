package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public Set<String> findPermissions(String geNumber);
    public Set<String> findRoles(String geNumber);
    public User findByGeNumber(String geNumber);
    public List<User> getUserList();
    public List<Record> getRecordByTime(String begin,String end,String userState);
    //查询员工列表
    public PageInfo<User> pageUser(User user, Integer pageNum, Integer pageSize);
    //添加员工
    public boolean addUser(User user);
    //判断工号是否已存在
    public boolean isNumberExist(String geNumber);
    //修改
    public boolean updateUser(User user);
    //删除
    public boolean delUser(String geNumber);
    public boolean updatePassword(String geNumber,String password,String newPassword);
    //锁定用户
    public boolean lockUser(int[] arr);
    //解锁用户
    public boolean clearUser(int[] arr);
    //查询通知列表
    public PageInfo<Inform> pageInform(Inform inform, Integer pageNum, Integer pageSize);
    //通过id获取inform
    public Inform getInformById(int id);
    //删除通知
    public boolean delInform(int id);
}
