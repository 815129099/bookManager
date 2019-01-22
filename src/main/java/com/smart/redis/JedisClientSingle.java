//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.smart.redis;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {
    @Autowired
    private JedisPool jedisPool;

    public JedisClientSingle() {
    }

    public String get(String key) {
        Jedis jedis = this.jedisPool.getResource();
        String str = jedis.get(key);
        jedis.close();
        return str;
    }

    public String set(String key, String value) {
        Jedis jedis = this.jedisPool.getResource();
        String str = jedis.set(key, value);
        jedis.close();
        return str;
    }

    public long hset(String hkey, String key, String value) {
        Jedis jedis = this.jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    public String hashGet(String key, String value) {
        Jedis jedis = this.jedisPool.getResource();
        String str = jedis.hget(key, value);
        jedis.close();
        return str;
    }

    public long hashDel(String hkey, String key) {
        return 0L;
    }

    public long tt1(String key) {
        return 0L;
    }

    public long expire(String key, int second) {
        Jedis jedis = this.jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    public long incr(String key) {
        return 0L;
    }

    public long del(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    public void setList(String key, List<?> list) {
        Jedis jedis = this.jedisPool.getResource();

        try {
            if (list != null && !list.isEmpty()) {
                jedis.set(key.getBytes(), SerializeUtil.serializeList(list));
            } else {
                jedis.set(key.getBytes(), "".getBytes());
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public List<?> getList(String key) {
        Jedis jedis = this.jedisPool.getResource();
        if (jedis != null && jedis.exists(key)) {
            byte[] data = jedis.get(key.getBytes());
            return SerializeUtil.unSerializeList(data);
        } else {
            return null;
        }
    }

    public void setObject(String key, Object o) {
        Jedis jedis = this.jedisPool.getResource();

        try {
            o = o == null ? new Object() : o;
            jedis.set(key.getBytes(), SerializeUtil.serializeObject(o));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public Object getObject(String key) {
        Jedis jedis = this.jedisPool.getResource();
        if (jedis != null && jedis.exists(key)) {
            byte[] data = jedis.get(key.getBytes());
            return SerializeUtil.unSerializeObject(data);
        } else {
            return null;
        }
    }

    public void clear() {
        Jedis jedis = this.jedisPool.getResource();
        jedis.flushDB();
    }

    public Object removeObject(String key) {
        return this.jedisPool.getResource().expire(SerializeUtil.serializeObject(key), 0);
    }

    public int getSize() {
        return Integer.valueOf(this.jedisPool.getResource().dbSize().toString());
    }

    public void setFile(String key, String path) {
        Jedis jedis = this.jedisPool.getResource();
        File fr = new File(path);

        try {
            jedis.set(key.getBytes(), SerializeUtil.serializeObject(fr));
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public File getFile(String key) {
        Jedis jedis = this.jedisPool.getResource();
        File file = (File)SerializeUtil.unSerializeObject(jedis.get(key.getBytes()));
        return file;
    }
}
