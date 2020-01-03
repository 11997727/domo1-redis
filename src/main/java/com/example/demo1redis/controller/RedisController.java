package com.example.demo1redis.controller;

import com.example.demo1redis.utils.RedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 吴成卓
 * @version V1.0
 * @Project: demo1-redis
 * @Package com.example.demo1redis
 * @Description:
 * @date 2020/1/3 星期五 22:02
 */
@Controller
public class RedisController {
    @Resource
    private RedisUtils redisUtils;

    @RequestMapping("/set1")
    @ResponseBody
    public String set(String key,String value){
        if(redisUtils.set(key,value)){
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/set2")
    @ResponseBody
    public String set(String key,String value,long time){
        if(redisUtils.set(key,value,time)){
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/expire")
    @ResponseBody
    public String expire(String key,long time){
        if(redisUtils.expire(key,time)){
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(String key){
        if(redisUtils.delete(key)){
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/getExpire")
    @ResponseBody
    public long getExpire(String key){
        long expire = redisUtils.getExpire(key);
        return expire;
    }

    @RequestMapping("/exists")
    @ResponseBody
    public String exists(String key){
        if(redisUtils.exists(key)){
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/get")
    @ResponseBody
    public String get(String key){
        String str= (String)redisUtils.get(key);
        return str;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(String key,String value){
        if(redisUtils.update(key,value)){
            return "success";
        }
        return "failed";
    }
}
