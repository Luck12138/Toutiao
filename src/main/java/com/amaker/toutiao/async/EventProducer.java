package com.amaker.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.amaker.toutiao.util.JedisAdapter;
import com.amaker.toutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 19:27
 * @Author: GHH
 * @Description:
 */
@Service
public class EventProducer {

    private  static final Logger logger=LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model){
        try {
            String json=JSONObject.toJSONString(model);
            String key=RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            logger.error("event producer 异常！"+e.getMessage());
            return false;
        }
    }

}
