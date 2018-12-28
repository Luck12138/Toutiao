package com.amaker.toutiao.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 14:26
 * @Author: GHH
 * @Description:
 */
public class EventModel {

    //事件类型
    private EventType type;
    //事件触发者
    private int actorId;
    //事件的id
    private int EntityType;
    private int EntityId;
    //事件的拥有者
    private int EntityOwnerId;
    //传入的信息
    private Map<String,String> exts=new HashMap<String, String>();


    public String getExts(String key){
        return exts.get(key);
    }

    public EventModel setExts(String key,String value){
        exts.put(key,value);
        return this;
    }

    public EventModel(EventType type){
        this.type=type;
    }

    public EventModel(){
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return EntityType;
    }

    public EventModel setEntityType(int entityType) {
        EntityType = entityType;
        return this;
    }

    public int getEntityId() {
        return EntityId;
    }

    public EventModel setEntityId(int entityId) {
        EntityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return EntityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        EntityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
