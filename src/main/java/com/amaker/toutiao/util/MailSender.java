package com.amaker.toutiao.util;

import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * @program: toutiao
 * @Date: 2018/12/29 0029 12:35
 * @Author: GHH
 * @Description:
 */
@Service
public class MailSender implements InitializingBean{

    private static final Logger logger=LoggerFactory.getLogger(MailSender.class);

    private JavaMailSenderImpl mailSender;


    public boolean sendWithHtmlTemplate(String to, String subject,
                                        Template template, Map<String,Object> model){
        try {


//            //邮件发送人
//            String nick=MimeUtility.encodeText("Luke");
            InternetAddress from=new InternetAddress("zhuyuxuan9611@qq.com");
           //创建邮件
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message);
           //freemarker引擎模板
            String result=FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            //发件人
            mimeMessageHelper.setFrom(from);
            //收件人
            mimeMessageHelper.setTo(to);
            //内容
            mimeMessageHelper.setText(result,true);
            //标题
            mimeMessageHelper.setSubject(subject);
            mailSender.send(message);
            return true;
        }catch (Exception e){
            logger.error("send mail 失败！"+e.getMessage());
            return false;
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        mailSender=new JavaMailSenderImpl();
        mailSender.setUsername("zhuyuxuan9611@qq.com");
        mailSender.setPassword("kcomfijpboimbcdc");
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties properties=new Properties();
        properties.put("mail.smtp.ssl.enable",true);
        mailSender.setJavaMailProperties(properties);

    }
}
