package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.HostHolder;
import com.amaker.toutiao.model.Message;
import com.amaker.toutiao.model.User;
import com.amaker.toutiao.model.ViewObject;
import com.amaker.toutiao.service.MessageService;
import com.amaker.toutiao.service.UserService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;


    @RequestMapping(value = "/msg/list",method = {RequestMethod.GET})
    public String msgList(Model model){

        try{
            if(hostHolder.getUser()==null){
                return "redirect:/?pop=1";
            }
            int localUser=hostHolder.getUser().getId();
            List<Message> conversationList = messageService.getConversationList(localUser, 0, 10);
            List<ViewObject> viewObjects=new ArrayList<ViewObject>();
            for(Message message:conversationList){
                ViewObject vo=new ViewObject();
                vo.set("conversation",message);
                int target=localUser==message.getFromId()?message.getToId():message.getFromId();
                User user = userService.selectUserById(target);
                vo.set("user",user);
                vo.set("unread",messageService.getConversationCount(localUser,message.getConversationId()));
                viewObjects.add(vo);
            }

            model.addAttribute("conversations",viewObjects);

        }catch (Exception e){
            logger.error("站内信获取失败！"+e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(value = "/msg/detail",method = {RequestMethod.GET})
    public String msgDetail(@RequestParam("conversationId") String conversationId,
                            Model model){
        try {
            List<ViewObject> messages=new ArrayList<>();
            List<Message> list = messageService.selectMessageByConId(conversationId, 0, 10);
            for(Message message:list){
                ViewObject vo=new ViewObject();
                vo.set("message",message);
                User user = userService.selectUserById(message.getFromId());
                if(user==null){
                    continue;
                }
                vo.set("headUrl",user.getHeadUrl());
                vo.set("name",user.getName());
                messages.add(vo);
        }
        model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取消息失败！"+e.getMessage());
        }
        return "letterDetail";
    }


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
