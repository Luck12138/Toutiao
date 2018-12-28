package com.amaker.toutiao.async.handler;

import com.amaker.toutiao.async.EventHandler;
import com.amaker.toutiao.async.EventModel;
import com.amaker.toutiao.async.EventType;
import com.amaker.toutiao.model.EntityType;
import com.amaker.toutiao.model.Message;
import com.amaker.toutiao.model.User;
import com.amaker.toutiao.service.MessageService;
import com.amaker.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/28 0028 15:11
 * @Author: GHH
 * @Description:
 */
@Component
public class LikeHandler implements EventHandler {


    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message=new Message();
        message.setFromId(2);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setConversationId(2>model.getActorId()?String.format("%d_%d",model.getActorId(),2):String.format("%d_%d",2,model.getActorId()));
        User user = userService.selectUserById(model.getActorId());
        message.setContent("用户"+user.getName()+"赞了你的资讯,http://127.0.0.1/news/"+model.getEntityId());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
