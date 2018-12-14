package com.amaker.toutiao.controller;

import com.amaker.toutiao.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: toutiao
 * @Date: 2018/12/4 0004 16:03
 * @Author: GHH
 * @Description:
 */
//@Controller
@ComponentScan("com.amaker.toutiao.dao")
public class IndexController {




    @Autowired
    UserDao userDao;


    @RequestMapping("/")
    @ResponseBody
    public String index(){

        return "hello world!";
    }

    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",defaultValue = "newcode") String key){



        return String.format("Group{%s},User{%d},Type{%d},Key{%s}",groupId,userId,type,key);
    }

    @RequestMapping("/news")
    public String news(Model model){

        model.addAttribute("value","vv1");

        List<String>  stringList= Arrays.asList(new String[]{"red","blue","green"});

        Map<String,String> map=new HashMap<String,String>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }

        return "news";
    }
}
