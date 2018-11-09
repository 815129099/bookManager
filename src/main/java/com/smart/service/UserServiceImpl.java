package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    com.smart.redis.JedisClient JedisClient;

    @Override
    public Set<String> findPermissions(String geNumber) {
        return userDao.findPermissions(geNumber);
    }

    @Override
    public Set<String> findRoles(String geNumber) {
        return userDao.findRoles(geNumber);
    }

    @Override
    public User findByGeNumber(String geNumber) {
        return userDao.findByGeNumber(geNumber);
    }

    //查询书籍列表
    public PageInfo<User> pageUser(User user, Integer pageNum, Integer pageSize) {
        System.out.println(user.getGeNumber()+","+user.getGeName()+","+user.getUserState());
        PageInfo<User> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<User> uList = userDao.listUser(user);
        page = new PageInfo<User>(uList);
        return page;
    }

    //添加员工
    @Transactional(rollbackFor={Exception.class})
    public boolean addUser(User user) {
        boolean isSuccess = false;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        user.setCreateTime(ft.format(dNow));
        user.setUpdateTime(ft.format(dNow));
        userDao.addUser(user);
        isSuccess = true;
        return isSuccess;
    }

    //判断工号是否已存在
    public boolean isNumberExist(String geNumber){
        boolean isSuccess = false;
        User user = null;
        user = (User)JedisClient.getObject(geNumber);
        if(user==null){
            user = userDao.getUserById(geNumber);
            if(user!=null){
                JedisClient.setObject(geNumber,(Object) user);
            }
        }

        if(StringUtils.isEmpty(user)){
            isSuccess = true;
        }
        return isSuccess;
    }

    //修改
    @Transactional(rollbackFor={Exception.class})
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        user.setUpdateTime(ft.format(dNow));
        userDao.updateUser(user);
        isSuccess = true;
        return isSuccess;
    }

    //删除
    @Transactional(rollbackFor={Exception.class})
    public boolean delUser(String geNumber) {
        boolean isSuccess = false;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        String updateTime = ft.format(dNow);
        userDao.delUser(geNumber,updateTime);
        isSuccess = true;
        return isSuccess;
    }
}
