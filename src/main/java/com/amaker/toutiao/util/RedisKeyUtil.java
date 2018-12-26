package com.amaker.toutiao.util;

/**
 * @program: toutiao
 * @Date: 2018/12/26 0026 11:03
 * @Author: GHH
 * @Description:
 */
public class RedisKeyUtil {

    public static String BIZ_LIKE="LIKE";
    public static String BIZ_DISLIKE="DISLIKE";
    public static String SPLIT=":";

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
}
