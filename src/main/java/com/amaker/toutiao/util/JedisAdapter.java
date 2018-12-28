package com.amaker.toutiao.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/25 0025 12:42
 * @Author: GHH
 * @Description:
 */
@Service
public class JedisAdapter implements InitializingBean {

    public static Logger logger=LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool=null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("localhost",6379);
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
            return 0;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
            return 0;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public Boolean sismember(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
            return false;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public long scard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
            return 0;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void set(String key,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("redis set 异常！"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }

        }

    }

    public void lpush(String key,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("redis set 异常！"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }

        }

    }

    public List<String> brpop(int timeout, String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("redis set 异常！"+e.getMessage());
            return null;
        }finally {
            if(jedis!=null){
                jedis.close();
            }

        }

    }

    public String get(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("redis get 异常！"+e.getMessage());
            return null;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void setObject(String key,Object object){
        set(key,JSON.toJSONString(object));
    }

    public <T> T getObject(String key,Class<T> clazz){
        String value=get(key);
        if(value!=null){
            return JSON.parseObject(value,clazz);
        }
        return null;
    }

//    public static void print(int index,Object obj){
//        System.out.println(String.format("%d,%s",index,obj.toString()));
//    }
//
//    public static void main(String[] args) {
//
//        Jedis jedis=new Jedis();
//        jedis.flushAll();
//        jedis.set("hello","hello");
//        print(1,jedis.get("hello"));
//        jedis.rename("hello","newhello");
//        print(1,jedis.get("newhello"));
//        jedis.setex("hello2",15,"hello2");
//
//        //数值操作
//        jedis.set("pv","100");
//        jedis.incr("pv");
//        print(2,jedis.get("pv"));
//        jedis.incrBy("pv",3);
//        print(2,jedis.get("pv"));
//        //列表
//        String listName="listA";
//        for(int i=0;i<10;i++){
//            jedis.lpush(listName,"a"+String.valueOf(i));
//        }
//
//        print(3,jedis.lrange(listName,0,12));
//        print(3,jedis.llen(listName));
//        print(3,jedis.lpop(listName));
//        print(3,jedis.llen(listName));
//        print(3,jedis.lrange(listName,0,12));
//        print(3,jedis.linsert(listName,ListPosition.BEFORE,"a4","xx"));
//        print(3,jedis.linsert(listName,ListPosition.AFTER,"a4","mm"));
//        print(3,jedis.lrange(listName,0,12));
//
//        //hash
//        String hashName="user";
//        jedis.hset(hashName,"name","zhangsan");
//        jedis.hset(hashName,"age","12");
//        jedis.hset(hashName,"phone","12343212342");
//        print(4,jedis.hget(hashName,"age"));
//        print(4,jedis.hgetAll(hashName));
//        jedis.hdel(hashName,"phone");
//        print(4,jedis.hgetAll(hashName));
//        jedis.hsetnx(hashName,"name","lssi");
//        jedis.hsetnx(hashName,"school","scc");
//        print(4,jedis.hgetAll(hashName));
//
//
//
//        //set
//        String likeSet1="likeSet1";
//        String likeSet2="likeSet2";
//        for(int i=0;i<9;i++){
//            jedis.sadd(likeSet1,String.valueOf(i));
//            jedis.sadd(likeSet2,String.valueOf(2*i));
//        }
//
//
//        print(5,jedis.smembers(likeSet1));
//        print(5,jedis.smembers(likeSet2));
//        print(5,jedis.sinter(likeSet1,likeSet2));
//        print(5,jedis.sunion(likeSet1,likeSet2));
//        print(5,jedis.sdiff(likeSet1,likeSet2));
//        print(5,jedis.sismember(likeSet1,"4"));
//        jedis.srem(likeSet1,"4");
//       print(5,jedis.smembers(likeSet1));
//       print(5,jedis.scard(likeSet1));
//       jedis.smove(likeSet2,likeSet1,"14");
//        print(5,jedis.smembers(likeSet1));
//
//        //zSet
//        String rankKey="rankKey";
//        jedis.zadd(rankKey,65,"jim");
//        jedis.zadd(rankKey,75,"tom");
//        jedis.zadd(rankKey,80,"jack");
//        jedis.zadd(rankKey,85,"luck");
//        jedis.zadd(rankKey,90,"mart");
//
//        print(6,jedis.zcard(rankKey));
//        print(6,jedis.zcount(rankKey,70,85));
//        print(6,jedis.zscore(rankKey,"tom"));
//        jedis.zincrby(rankKey,3,"tom");
//        print(6,jedis.zscore(rankKey,"tom"));
//        jedis.zincrby(rankKey,3,"luc");
//        print(6,jedis.zcount(rankKey,0,100));
//        print(6,jedis.zrange(rankKey,1,3));
//        print(6,jedis.zrevrange(rankKey,1,3));
//        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,0,100)){
//            print(7,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
//        }
//
//        print(8,jedis.zrank(rankKey,"tom"));
//        print(8,jedis.zrevrank(rankKey,"tom"));
//    }
//

}
