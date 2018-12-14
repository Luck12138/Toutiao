package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.UserDao;
import com.amaker.toutiao.model.User;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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
