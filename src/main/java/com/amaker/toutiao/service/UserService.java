package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.LoginTicketDao;
import com.amaker.toutiao.dao.UserDao;
import com.amaker.toutiao.model.LoginTicket;
import com.amaker.toutiao.model.User;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: toutiao
 * @Date: 2018/12/11 0011 13:58
 * @Author: GHH
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;


    public String addLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*12);
        loginTicket.setExpired(date);
        loginTicketDao.addLoginTicket(loginTicket);

        return loginTicket.getTicket();

    }

    public Map<String,Object> register(String username, String password){

        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isBlank(username)){
            map.put("magname","用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("magpwd","密码不能为空");
            return map;
        }

        User user=userDao.selectByName(username);

        if(user!=null){
            map.put("msgname","用户名已被注册");
            return map;
        }

        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setPassword(TouTiaoUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        return map;
    }


    public Map<String,Object> login(String username, String password){

        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isBlank(username)){
            map.put("magname","用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("magpwd","密码不能为空");
            return map;
        }

        User user=userDao.selectByName(username);

        if(user==null){
            map.put("msgname","用户名不存在");
            return map;
        }

        if(!TouTiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码错误");
            return map;
        }
        map.put("userId",user.getId());
       //返还用户ticket
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }


    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }

    public User selectUserById(int id){
        return userDao.selectById(id);
    }

    public void updatePassword(User user){
        userDao.updatePassword(user);
    }

    public void deleteUserById(int id){
        userDao.deleteById(id);
    }
}
