package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.MessageDao;
import com.amaker.toutiao.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/20 0020 21:24
 * @Author: GHH
 * @Description:
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public void addMessage(Message message){
        messageDao.addMessage(message);
    }

    public List<Message> selectMessageByConId(String conversation_id,int offset,int limit){
        return messageDao.selectMessageByConId(conversation_id,offset,limit);
    }
}
