package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.EntityType;
import com.amaker.toutiao.model.HostHolder;
import com.amaker.toutiao.service.LikeService;
import com.amaker.toutiao.service.NewsService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: toutiao
 * @Date: 2018/12/26 0026 14:47
 * @Author: GHH
 * @Description:
 */
@Controller
public class LikeController {


    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/like",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId){

        int localUser=hostHolder.getUser().getId();
        long like = likeService.like(localUser, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount((int)like,newsId);
        return TouTiaoUtil.getJsonString(0,String.valueOf(like));
    }


    @RequestMapping(value = "/dislike",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId){
        int localUser=hostHolder.getUser().getId();
        long like = likeService.disLike(localUser, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount((int)like,newsId);
        return TouTiaoUtil.getJsonString(0,String.valueOf(like));
    }
}
