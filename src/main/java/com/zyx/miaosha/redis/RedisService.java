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
    @Autowired
    RedisConfig redisConfig;
   public <T> T get(String key,Class<T> clazz){
        Jedis jedis=null;
      try {
          jedis=jedisPool.getResource();
          String str=jedis.get(key);
          T t=stringToBean(str,clazz);
          return t;

      }finally {
       returnToPool(jedisPool);
      }
   }

    public <T> boolean set(String key,Class<T> clazz){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();

       //  jedis.set(key,clazz);
       //     T t=stringToBean(str,clazz);
            return true;

        }finally {
            returnToPool(jedisPool);
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

    private void returnToPool(JedisPool jedisPool) {
       if (jedisPool!=null){
           jedisPool.close();
       }
    }

    @Bean
    public JedisPool jedisPoolFactory(){

       JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
       jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
       jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
       jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
     JedisPool jedisPool=new JedisPool(jedisPoolConfig,redisConfig.getHost(),redisConfig.getPort()
             ,redisConfig.getTimeout()*1000,null,0,null);
     return  jedisPool;
   }
}
