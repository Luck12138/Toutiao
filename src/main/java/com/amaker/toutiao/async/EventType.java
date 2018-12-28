package com.amaker.toutiao.async;

import com.amaker.toutiao.model.EntityType;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 14:27
 * @Author: GHH
 * @Description:
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    EventType(int value){this.value=value;}
    public int getValue(){return value;}

}
