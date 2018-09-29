package com.smart.redis;

import com.smart.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.util.List;

public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get(key);
        jedis.close();
        return str;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.set(key,value);
        jedis.close();
        return str;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(hkey,key,value);
        jedis.close();
        return result;
    }

    @Override
    public String hashGet(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.hget(key,value);
        jedis.close();
        return str;
    }

    @Override
    public long hashDel(String hkey, String key) {
        return 0;
    }

    @Override
    public long tt1(String key) {
        return 0;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key,second);
        jedis.close();
        return result;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public void setList(String key, List<?> list) {
        Jedis jedis = jedisPool.getResource();
        try{
            if(list != null && !list.isEmpty()){
                jedis.set(key.getBytes(),SerializeUtil.serializeList(list));
            }else{
                jedis.set(key.getBytes(), "".getBytes());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<?> getList(String key) {
        Jedis jedis = jedisPool.getResource();
        if(jedis==null || !jedis.exists(key)){
            return null;
        }
        byte[] data = jedis.get(key.getBytes());
        return SerializeUtil.unSerializeList(data);
    }

    @Override
    public void setObject(String key, Object o) {
        Jedis jedis = jedisPool.getResource();
        try{
            o = o == null ? new Object():o;
            jedis.set(key.getBytes(),SerializeUtil.serializeObject(o));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object getObject(String key) {
        Jedis jedis = jedisPool.getResource();
        if(jedis == null || !jedis.exists(key)){
            return null;
        }
        byte[] data = jedis.get(key.getBytes());
        return (Object)SerializeUtil.unSerializeObject(data);
    }

    @Override
    public void clear() {
        Jedis jedis = jedisPool.getResource();
        jedis.flushDB();
    }

    @Override
    public Object removeObject(String key) {
        return jedisPool.getResource().expire(SerializeUtil.serializeObject(key), 0);
    }

    @Override
    public int getSize() {
        return Integer.valueOf(jedisPool.getResource().dbSize().toString());
    }

    //保存文件方法
    public void setFile(String key,String path){
        Jedis jedis = jedisPool.getResource();
        File fr = new File(path);
        try{
            jedis.set(key.getBytes(), SerializeUtil.serializeObject(fr));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //读取文件对象方法
    public File getFile(String key){
        Jedis jedis = jedisPool.getResource();
        File file = (File)SerializeUtil.unSerializeObject(jedis.get(key.getBytes()));
        return file;
    }
}
