package com.amaker.toutiao;

import com.amaker.toutiao.dao.CommentDao;
import com.amaker.toutiao.dao.LoginTicketDao;
import com.amaker.toutiao.dao.NewsDao;
import com.amaker.toutiao.dao.UserDao;
import com.amaker.toutiao.model.*;
import com.amaker.toutiao.util.JedisAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
@ComponentScan("com.amaker.toutiao.dao")
public class JedisTests {


    @Autowired
    private JedisAdapter jedisAdapter;

    @Test
    public void contextLoads() {

        User user=new User();
        user.setName("张三");
        user.setSalt("slat");
        user.setPassword("password");
        user.setHeadUrl("http://images.nowcoder.com/head/2t.png");

        jedisAdapter.setObject("user",user);

        User u = jedisAdapter.getObject("user", User.class);
        System.out.println(ToStringBuilder.reflectionToString(u));

    }



}
