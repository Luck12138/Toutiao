package com.amaker.toutiao.service;

import com.amaker.toutiao.util.JedisAdapter;
import com.amaker.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: toutiao
 * @Date: 2018/12/26 0026 10:34
 * @Author: GHH
 * @Description:
 */
@Service
public class LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 喜欢返回1，不喜欢返回-1，否则返回0
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(likeKey,String .valueOf(userId))){
            return 1;
        }
        String disLike=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        return jedisAdapter.sismember(disLike,String.valueOf(disLike))?-1:0;

    }

    public long like(int userId,int entityType,int entityId){
        String likeKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        String disLikeKey=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId,int entityType, int entityId){
        String disLikeKey=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));

        String likeKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));

        return jedisAdapter.scard(disLikeKey);
    }
}
