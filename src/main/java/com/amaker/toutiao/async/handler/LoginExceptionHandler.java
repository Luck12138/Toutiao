package com.amaker.toutiao.async.handler;

import com.amaker.toutiao.async.EventHandler;
import com.amaker.toutiao.async.EventModel;
import com.amaker.toutiao.async.EventType;
import com.amaker.toutiao.dao.MessageDao;
import com.amaker.toutiao.model.Message;
import com.amaker.toutiao.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 21:51
 * @Author: GHH
 * @Description:
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    private MessageService messageService;
    @Override
    public void doHandler(EventModel model) {
        Message message=new Message();
        message.setFromId(2);
        message.setToId(model.getActorId());
        message.setCreatedDate(new Date());
        message.setConversationId(2>model.getActorId()?String.format("%d_%d",model.getActorId(),2):String.format("%d_%d",2,model.getActorId()));
        message.setContent("你上次的登陆IP异常");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
