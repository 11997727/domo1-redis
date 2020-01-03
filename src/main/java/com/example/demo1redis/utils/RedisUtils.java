package com.example.demo1redis.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 吴成卓
 * @version V1.0
 * @Project: demo1-redis
 * @Package com.example.demo1redis.utils
 * @Description:
 * @date 2020/1/3 星期五 21:31
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String,Object>redisTemplate;

    /**
     * 添加
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,String value){
        try{
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            ValueOperations<String, Object> vo = redisTemplate.opsForValue();
            vo.set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 给一个key设置过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,long time){
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                boolean flag=false;
                try {
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    redisTemplate.setValueSerializer(new StringRedisSerializer());
                    byte[] keys = new StringRedisSerializer().serialize(key);
                    flag= redisConnection.expire(keys, time);
                }catch (Exception e){
                    e.printStackTrace();
                    return flag;
                }
                return flag;
            }
        });
    }

    /**
     * 添加的同时设置时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,String value,long time){
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            ValueOperations<String, Object> vo = redisTemplate.opsForValue();
            vo.set(key,value);
            expire(key,time);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查看
     * @param key
     * @return
     */
    public Object get(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    /**
     * 获取key的时间
     * @param key
     * @return
     */
    public long getExpire(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return  redisTemplate.getExpire(key);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public boolean delete(String key){
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key 是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        Object o = vo.get(key);
        if(o==null||o==""){
            return false;
        }
        return true;
    }

    /**
     * 修改
     * @param key
     * @return
     */
    public boolean update(String key,String value){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Long time = redisTemplate.getExpire(key);
        if(time==null||time==0||time==-2){
            return false;
        }
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key,value);
        if(time!=0){
           expire(key,time);
        }
        return  true;
    }
}
