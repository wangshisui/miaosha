package com.zyx.miaosha.redis;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author:zhangyx
 * @Date:Created in 21:392018/11/11
 * @Modified By:
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 获取对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */

   public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis=null;
      try {
          jedis=jedisPool.getResource();
          //生成真的key
          String relkey=prefix.getPrefix()+key;
          String str=jedis.get(relkey);
          T t=stringToBean(str,clazz);
          return t;

      }finally {
       returnToPool(jedis);
      }
   }

    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis=null;
        try {
         jedis=jedisPool.getResource();

     String str=beanToString(value);
     if(str==null||str.length()<=0){
         return false;
     }
            //生成真的key
            String relkey=prefix.getPrefix()+key;
           int seconds= prefix.expireSeconds();
           if(seconds<=0){
               jedis.set(relkey,str);
           }else {
               jedis.setex(relkey,seconds,str);
           }

            return true;

        }finally {
            returnToPool(jedis);
        }
    }
//判断key是否存在
    public <T> boolean exists(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();

            //生成真的key
            String relkey=prefix.getPrefix()+key;
           return jedis.exists(relkey);
        }finally {
            returnToPool(jedis);
        }
    }
  //增加key
    public <T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();

            //生成真的key
            String relkey=prefix.getPrefix()+key;
            return jedis.incr(relkey);
        }finally {
            returnToPool(jedis);
        }
    }

    //删除delete
    public boolean delete(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();

            //生成真的key
            String relkey=prefix.getPrefix()+key;
            long ret= jedis.del(relkey);
            return ret>0;
        }finally {
            returnToPool(jedis);
        }
    }
  //减少key
    public <T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();

            //生成真的key
            String relkey=prefix.getPrefix()+key;
            return jedis.decr(relkey);
        }finally {
            returnToPool(jedis);
        }
    }


    private <T> String beanToString(T value) {
        if (value==null){
            return null;
        }
        Class<?> clazz=value.getClass();
        if (clazz==int.class||clazz==Integer.class){
        return ""+value;
        }else if (clazz==String.class){
        return (String )value;
        }else if (clazz==long.class||clazz==Long.class){
       return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String str,Class<T> clazz) {
    if(str==null||str.length()<0||clazz==null){
        return null;
    }
        if (clazz==int.class||clazz==Integer.class){
            return (T) Integer.valueOf(str);
        }else if (clazz==String.class){
            return (T )str;
        }else if (clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
       if (jedis!=null){
           jedis.close();
       }
    }


}
