package com.amaker.toutiao.model;

import org.springframework.stereotype.Component;

/**
 * @program: toutiao
 * @Date: 2018/12/15 0015 19:31
 * @Author: GHH
 * @Description:
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users=new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}

