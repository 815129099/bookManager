package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
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
        user.setCreateTime(DateUtil.getDate());
        user.setUpdateTime(DateUtil.getDate());
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
        user.setUpdateTime(DateUtil.getDate());
        userDao.updateUser(user);
        isSuccess = true;
        return isSuccess;
    }

    //删除
    @Transactional(rollbackFor={Exception.class})
    public boolean delUser(String geNumber) {
        boolean isSuccess = false;
        userDao.delUser(geNumber,DateUtil.getDate());
        isSuccess = true;
        return isSuccess;
    }

    //修改密码
    @Transactional(rollbackFor={Exception.class})
    @Override
    public boolean updatePassword(String geNumber, String password, String newPassword) {
        boolean isSuccess = false;
        String str = userDao.findByGeNumber(geNumber).getPassword();
        if(str.equals(password)){
            userDao.updatePassword(newPassword,geNumber,DateUtil.getDate());
            isSuccess = true;
        }
        return isSuccess;
    }
    //批准申请
    @Transactional(rollbackFor={Exception.class})
    @Override
    public boolean lockUser(int[] arr) {
        boolean isSuccess = false;
        for (int id:arr) {
            userDao.lockUser(id,DateUtil.getDate());
        }
        isSuccess = true;
        return isSuccess;
    }

    //退回申请
    @Transactional(rollbackFor={Exception.class})
    @Override
    public boolean clearUser(int[] arr) {
        boolean isSuccess = false;
        for (int id:arr) {
            userDao.clearUser(id,DateUtil.getDate());
        }
        isSuccess = true;
        return isSuccess;
    }

}
