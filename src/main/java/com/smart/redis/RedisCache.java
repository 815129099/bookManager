package com.smart.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * 使用第三方缓存服务器，处理二级缓存
 */
public class RedisCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * Jedis客户端
     */

    @Autowired
    private Jedis redisClient = createClient();

    private String id;

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        System.out.println("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public void clear() {
        redisClient.flushDB();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Object getObject(Object key) {
        byte[] ob = redisClient.get(SerializeUtil.serializeObject(key.toString()));
        if (ob == null) {
            return null;
        }
        Object value = SerializeUtil.unSerializeObject(ob);
        return value;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public int getSize() {
        return Integer.valueOf(redisClient.dbSize().toString());
    }

    @Override
    public void putObject(Object key, Object value) {
        redisClient.set(SerializeUtil.serializeObject(key.toString()), SerializeUtil.serializeObject(value));
    }

    @Override
    public Object removeObject(Object key) {
        return redisClient.expire(SerializeUtil.serializeObject(key.toString()), 0);
    }

    protected static Jedis createClient() {

        try {
            @SuppressWarnings("resource")
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379,10000,"960521");
            return pool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("初始化连接池错误");
    }
}
