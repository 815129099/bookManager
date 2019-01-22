
package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
import com.smart.redis.JedisClient;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    JedisClient JedisClient;

    public UserServiceImpl() {
    }

    public Set<String> findPermissions(String geNumber) {
        return this.userDao.findPermissions(geNumber);
    }

    public Set<String> findRoles(String geNumber) {
        return this.userDao.findRoles(geNumber);
    }

    public User findByGeNumber(String geNumber) {
        return this.userDao.findByGeNumber(geNumber);
    }

    public PageInfo<User> pageUser(User user, Integer pageNum, Integer pageSize) {
        System.out.println(user.getGeNumber() + "," + user.getGeName() + "," + user.getUserState());
        PageInfo<User> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<User> uList = this.userDao.listUser(user);
        page = new PageInfo(uList);
        return page;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean addUser(User user) {
        boolean isSuccess = false;
        user.setCreateTime(DateUtil.getDate());
        user.setUpdateTime(DateUtil.getDate());
        if (Integer.parseInt(user.getUserMoney()) >= 50) {
            user.setAuthority("user");
            user.setRole("user");
        } else {
            user.setRole("guest");
            user.setAuthority("guest");
        }

        this.userDao.addUser(user);
        isSuccess = true;
        return isSuccess;
    }

    public boolean isNumberExist(String geNumber) {
        boolean isSuccess = false;
        User user = null;
        user = (User)this.JedisClient.getObject(geNumber);
        if (user == null) {
            user = this.userDao.getUserById(geNumber);
            if (user != null) {
                this.JedisClient.setObject(geNumber, user);
            }
        }

        if (StringUtils.isEmpty(user)) {
            isSuccess = true;
        }

        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        user.setUpdateTime(DateUtil.getDate());
        this.userDao.updateUser(user);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean delUser(String geNumber) {
        boolean isSuccess = false;
        this.userDao.delUser(geNumber, DateUtil.getDate());
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updatePassword(String geNumber, String password, String newPassword) {
        boolean isSuccess = false;
        String str = this.userDao.findByGeNumber(geNumber).getPassword();
        if (str.equals(password)) {
            this.userDao.updatePassword(newPassword, geNumber, DateUtil.getDate());
            isSuccess = true;
        }

        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean lockUser(int[] arr) {
        boolean isSuccess = false;
        int[] var3 = arr;
        int var4 = arr.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            this.userDao.lockUser(id, DateUtil.getDate());
        }

        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean clearUser(int[] arr) {
        boolean isSuccess = false;
        int[] var3 = arr;
        int var4 = arr.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            this.userDao.clearUser(id, DateUtil.getDate());
        }

        isSuccess = true;
        return isSuccess;
    }

    public PageInfo<Inform> pageInform(Inform inform, Integer pageNum, Integer pageSize) {
        System.out.println(inform.getTitle());
        PageInfo<Inform> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Inform> iList = this.userDao.listInform(inform);
        page = new PageInfo(iList);
        return page;
    }

    public Inform getInformById(int id) {
        return this.userDao.getInformById(id);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean delInform(int id) {
        boolean isSuccess = false;
        this.userDao.deleteInform(id, DateUtil.getDate());
        isSuccess = true;
        return isSuccess;
    }

    public User getUserByGrNumber(String geNumber) {
        return this.userDao.getUserById(geNumber);
    }

    public List<User> getUserList() {
        return this.userDao.getUserList();
    }

    public List<Record> getRecordByTime(String begin, String end, String userState) {
        return this.userDao.getRecordByTime(begin, end, userState);
    }
}
