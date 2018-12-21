package com.amaker.toutiao;

import com.amaker.toutiao.dao.CommentDao;
import com.amaker.toutiao.dao.LoginTicketDao;
import com.amaker.toutiao.dao.NewsDao;
import com.amaker.toutiao.dao.UserDao;
import com.amaker.toutiao.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
@ComponentScan("com.amaker.toutiao.dao")
public class InitDatabaseTests {

    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    CommentDao commentDao;


    @Test
    public void contextLoads() {

        Random random=new Random();

        for(int i=0;i<10;i++){
            User user=new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                    random.nextInt(1000)));
            user.setName(String.format("user%d",i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);

            user.setPassword("newpassword");
            userDao.updatePassword(user);

            News news=new News();
            news.setCommentCount(i);
            Date date=new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",
                    random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("Title{%d}",i));
            news.setLink(String.format("http://wwww.nowcoder.com/%d.html",i));
            newsDao.addNews(news);

            LoginTicket ticket=new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setTicket(String.format("ticket%d",i));
            ticket.setExpired(date);
            loginTicketDao.addLoginTicket(ticket);

            loginTicketDao.updateStatus(ticket.getTicket(),2);

            for(int j=0;j<3;j++){
                Comment comment=new Comment();
                comment.setUserId(i+1);
                comment.setCreatedDate(new Date());
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                comment.setStatus(0);
                comment.setContent("comment"+String.valueOf(i));
                commentDao.addComment(comment);
            }


        }

        Assert.assertEquals("newpassword",userDao.selectById(1).getPassword());
        Assert.assertEquals(1,loginTicketDao.selectByTicket("ticket0").getUserId());
        Assert.assertEquals(2,loginTicketDao.selectByTicket("ticket1").getStatus());
        Assert.assertNotNull(commentDao.selectCommentByIdAndType(1,EntityType.ENTITY_NEWS).get(0));
    }

}
