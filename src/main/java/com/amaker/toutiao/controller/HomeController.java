package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.EntityType;
import com.amaker.toutiao.model.HostHolder;
import com.amaker.toutiao.model.News;
import com.amaker.toutiao.model.ViewObject;
import com.amaker.toutiao.service.LikeService;
import com.amaker.toutiao.service.NewsService;
import com.amaker.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/11 0011 13:03
 * @Author: GHH
 * @Description:
 */
@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;


    public List<ViewObject> getNews(int userId,int offset,int limit){

        List<News> latestNews = newsService.getLatestNews(userId,offset,limit);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        int localUser = hostHolder.getUser()!=null?hostHolder.getUser().getId():0;
        for(News news:latestNews){
            ViewObject object=new ViewObject();
            object.set("news",news);
            object.set("user",userService.selectUserById(news.getUserId()));
            if(localUser!=0){
                object.set("like",likeService.getLikeStatus(localUser,EntityType.ENTITY_NEWS,news.getId()));
            }else {
                object.set("like",0);
            }

            vos.add(object);
        }
        return vos;
    }

    @RequestMapping("/")
    public String index(Model model,
                         @RequestParam(value = "pop", defaultValue = "0") int pop){

        model.addAttribute("vos",getNews(0,0,10));
        model.addAttribute("pop",pop);
        return "home";
    }

    @RequestMapping("/user/{userId}")
    public String userId(@PathVariable(value = "userId") int userId,
                         Model model,
                         @RequestParam(value = "pop", defaultValue = "0" )int pop){
        model.addAttribute("vos",getNews(userId,0,10));
        model.addAttribute("pop",pop);

        return "home";
    }




}
