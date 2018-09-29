package com.smart.redis;

import com.smart.bean.Book;

import java.io.File;
import java.util.List;

public interface JedisClient {
    String get(String key);
    String set(String key,String value);
    public void setList(String key,List<?> value);
    public List<?> getList(String key);
    public void setObject(String key,Object o);
    public Object getObject(String key);
    public void clear();
    public Object removeObject(String str);
    public int getSize();
    public void setFile(String key,String path);
    public File getFile(String key);


    String hashGet(String key,String value);//获取存储结构为hashMap的类型数据
    long hset(String hkey,String key, String value);
    long incr(String key);
    long expire(String key,int second);
    long tt1(String key);
    long del(String key);//删除数据
    long hashDel(String hkey,String key);
}
