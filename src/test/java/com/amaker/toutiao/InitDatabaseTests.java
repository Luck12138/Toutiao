package com.amaker.toutiao;

import com.amaker.toutiao.dao.NewsDao;
import com.amaker.toutiao.dao.UserDao;
import com.amaker.toutiao.model.News;
import com.amaker.toutiao.model.User;
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





        }

        Assert.assertEquals("newpassword",userDao.selectById(1).getPassword());
        Assert.assertNull(userDao.selectById(1));
    }

}
