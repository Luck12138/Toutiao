package com.amaker.toutiao.async.handler;

import com.amaker.toutiao.async.EventHandler;
import com.amaker.toutiao.async.EventModel;
import com.amaker.toutiao.async.EventType;
import com.amaker.toutiao.model.Message;
import com.amaker.toutiao.service.MessageService;
import com.amaker.toutiao.util.MailSender;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.util.*;

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
    @Autowired
    private MailSender mailSender;
    @Override
    public void doHandler(EventModel model) {
        Message message=new Message();
        message.setFromId(2);
        message.setToId(model.getActorId());
        message.setCreatedDate(new Date());
        message.setConversationId(2>model.getActorId()?String.format("%d_%d",model.getActorId(),2):String.format("%d_%d",2,model.getActorId()));
        message.setContent("你上次的登陆IP异常");
       // messageService.addMessage(message);

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("username",model.getExts("username"));
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:templates/mails/");//模板目录，我在classes新建了model目录
        Template template = null;
        try {
            template = factory.createConfiguration().getTemplate("welcome.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mailSender.sendWithHtmlTemplate(model.getExts("to"),"登录异常！",template,map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
