package com.amaker.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.amaker.toutiao.util.JedisAdapter;
import com.amaker.toutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 19:52
 * @Author: GHH
 * @Description:
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {

    private static final Logger logger=LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType,List<EventHandler>> map=new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            Map<String, EventHandler> beansOfType = applicationContext.getBeansOfType(EventHandler.class);
            if(beansOfType!=null){
                for(Map.Entry<String,EventHandler> entry:beansOfType.entrySet()){
                    List<EventType> supportEventTypes = entry.getValue().getSupportEventTypes();
                    for(EventType type:supportEventTypes){
                        if(!map.containsKey(type)){
                            map.put(type,new ArrayList<EventHandler>());
                        }
                        map.get(type).add(entry.getValue());
                    }
                }
            }

        }catch (Exception e){
            logger.error("event consumer 异常！"+e.getMessage());
        }


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key=RedisKeyUtil.getEventQueueKey();
                    List<String> messages=jedisAdapter.brpop(0,key);
                    for(String msg:messages){
                        if(msg.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(msg, EventModel.class);
                        if(!map.containsKey(eventModel.getType())){
                            logger.error("无此事件！");
                            continue;
                        }
                        for(EventHandler handler:map.get(eventModel.getType())){
                            handler.doHandler(eventModel);
                        }
                    }

                }
            }
        });
        thread.start();
    }





    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
