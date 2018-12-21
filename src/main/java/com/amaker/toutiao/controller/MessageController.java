package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.Message;
import com.amaker.toutiao.service.MessageService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @program: toutiao
 * @Date: 2018/12/20 0020 21:24
 * @Author: GHH
 * @Description:
 */
@Controller
public class MessageController {

    public static Logger logger=LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/msg/addMessage",method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content){
        try{
            Message message=new Message();
            message.setToId(toId);
            message.setFromId(fromId);
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setConversationId(fromId<toId?String.format("%d_%d",fromId,toId):String.format("%d_%d",toId,fromId));
            messageService.addMessage(message);
            return TouTiaoUtil.getJsonString(message.getId());
        }
        catch (Exception e){
            logger.error("信息发送异常！"+e.getMessage());
            return TouTiaoUtil.getJsonString(1,"信息发送异常");
        }
    }
}
