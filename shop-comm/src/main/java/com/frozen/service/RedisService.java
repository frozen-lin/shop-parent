package com.frozen.service;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <program> shopparent </program>
 * <description> Redis服务类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 20:05
 **/
@Repository
@Getter
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * <description> redis存储字符串键值对 </description>
     * @param key : 键
     * @param value : 值
     * @param timeout : 超时时长
     * @param timeUnit : 时长单位
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:19
     */
    public void setString(String key, String value, Long timeout, TimeUnit timeUnit){
        if (Objects.isNull(timeout)){
            stringRedisTemplate.opsForValue().set(key, value);
        }else{
            stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }
    }
    /**
     * <description> redis存储字符串键值对,超时以秒为单位 </description>
     * @param key : 键
     * @param value : 值
     * @param timeoutSecond : 超时秒数
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:20
     */
    public void setString(String key, String value, Long timeoutSecond){
        if (Objects.isNull(timeoutSecond)){
            stringRedisTemplate.opsForValue().set(key, value);
        }else{
            stringRedisTemplate.opsForValue().set(key, value, timeoutSecond, TimeUnit.SECONDS);
        }
    }
    /**
     * <description> redis存储字符串键值对,无超时时间 </description>
     * @param key :  键
     * @param value :  值
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:21
     */
    public void setString(String key, String value){
        setString(key,value,null);
    }

    /**
     * <description> 将jsonObj转成json串以字符串键值对进行存储 </description>
     * @param key : 键
     * @param jsonObj : 值(对象)
     * @param timeout : 超时时长
     * @param timeUnit : 时长单位
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:19
     */
    public void setJsonObject(String key,Object jsonObj,Long timeout,TimeUnit timeUnit){
        String jsonString = null;
        if (!Objects.isNull(jsonObj)){
            jsonString = JSONObject.toJSONString(jsonObj);
        }
        setString(key, jsonString, timeout, timeUnit);
    }
    /**
     * <description> 将jsonObj转成json串以字符串键值对进行存储,超时以秒为单位 </description>
     * @param key : 键
     * @param jsonObj : 值(对象)
     * @param timeoutSecond : 超时秒数
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:34
     */
    public void setJsonObject(String key,Object jsonObj,Long timeoutSecond){
        String jsonString = null;
        if (!Objects.isNull(jsonObj)){
            jsonString = JSONObject.toJSONString(jsonObj);
        }
        setString(key, jsonString, timeoutSecond);
    }
    /**
     * <description> 将jsonObj转成json串以字符串键值对进行存储,超时以秒为单位 </description>
     * @param key : 键
     * @param jsonObj :  值(对象)
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:35
     */
    public void setJsonObject(String key,Object jsonObj){
        String jsonString = null;
        if (!Objects.isNull(jsonObj)){
            jsonString = JSONObject.toJSONString(jsonObj);
        }
        setString(key, jsonString);
    }

    /**
     * <description> redis获取与key对应的字符串 </description>
     * @param key : 键
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/3/25 20:37
     */
    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * <description> 将存入的json串对象以clazz对象形式取出 </description>
     * @param key :  键
     * @param clazz :  json串转换对象的class类型
     * @return : T
     * @author : lw
     * @date : 2020/3/25 20:36
     */
    public <T> T getJsonObject(String key,Class<T> clazz){
        String jsonString = getString(key);
        T obj = JSONObject.parseObject(jsonString, clazz);
        return obj;
    }
    /**
     * <description> 删除该键对应的值 </description>
     * @param key :  键
     * @return : void
     * @author : lw
     * @date : 2020/3/25 20:38
     */
    public void delKey(String key){
        stringRedisTemplate.delete(key);
    }
}
