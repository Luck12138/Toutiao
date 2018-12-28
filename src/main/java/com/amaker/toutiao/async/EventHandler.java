package com.amaker.toutiao.async;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 14:58
 * @Author: GHH
 * @Description:
 */
public interface EventHandler {
    void doHandler(EventModel model);
    List<EventType> getSupportEventTypes();
}
