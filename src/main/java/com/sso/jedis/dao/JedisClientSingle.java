package com.sso.jedis.dao;

import redis.clients.jedis.Jedis;

import com.monitor.utils.RedisUtils;

public class JedisClientSingle implements JedisClient {
    //private final String secret="xiangrong@1993";
    
    @Override
    public String get(String key) {
        Jedis jedis = RedisUtils.getJedis();
        
        String string = jedis.get(key);
        jedis.close();
        return string;
    }
    
    @Override
    public String set(String key, String value) {
        Jedis jedis = RedisUtils.getJedis();
        
        String string = jedis.set(key, value);
        jedis.close();
        return string;
    }
    
    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = RedisUtils.getJedis();
        
        String string = jedis.hget(hkey, key);
        jedis.close();
        return string;
    }
    
    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = RedisUtils.getJedis();
        
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }
    
    @Override
    public long incr(String key) {
        Jedis jedis = RedisUtils.getJedis();
        
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }
    
    @Override
    public long expire(String key, int second) {
        Jedis jedis = RedisUtils.getJedis();
        
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }
    
    @Override
    public long ttl(String key) {
        Jedis jedis = RedisUtils.getJedis();
        
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }
    
    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = RedisUtils.getJedis();
        long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }
}
