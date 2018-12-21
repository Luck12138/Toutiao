package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.*;
import com.amaker.toutiao.service.CommentService;
import com.amaker.toutiao.service.NewsService;
import com.amaker.toutiao.service.QiniuService;
import com.amaker.toutiao.service.UserService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/17 0017 15:24
 * @Author: GHH
 * @Description:
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    public static Logger logger=LoggerFactory.getLogger(NewsController.class);

    @RequestMapping(value = "/uploadImage",method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try{
//            String fileUrl = newsService.saveImage(file);
            String fileUrl=qiniuService.saveImage(file);
            if(fileUrl==null){
                TouTiaoUtil.getJsonString(1,"图片上传失败！");
            }
            return TouTiaoUtil.getJsonString(0,fileUrl);
        }
        catch (Exception e){

            logger.error("图片上传异常！"+e.getMessage());
            return TouTiaoUtil.getJsonString(1,"图片上传异常！");

        }
    }


    @RequestMapping(value = "/image",method = {RequestMethod.GET})
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response){
        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(TouTiaoUtil.IMG_DIR+imageName)),
                    response.getOutputStream());
        } catch (Exception e) {
            logger.error("图片读取失败！"+e.getMessage());
        }
    }


    @RequestMapping(value = "/user/addNews",method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link){
        try {
            News news=new News();
            if(hostHolder.getUser()==null){
                news.setUserId(3);
            }else {
                news.setUserId(hostHolder.getUser().getId());
            }
            news.setLink(link);
            news.setImage(image);
            news.setCreatedDate(new Date());
            news.setTitle(title);
            newsService.addNews(news);
            return TouTiaoUtil.getJsonString(0,"资讯添加成功！");

        }catch (Exception e){
            logger.error("资讯添加失败！"+e.getMessage());
            return TouTiaoUtil.getJsonString(1,"资讯添加失败！");
        }
    }


    @RequestMapping(value = "/news/{newsId}",method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId,
                             Model model){

        News news = newsService.selectNewsById(newsId);
        if(news!=null){
            List<Comment> comments = commentService.selectComment(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVos=new ArrayList<ViewObject>();
            for(Comment comment:comments){
                ViewObject vo=new ViewObject();
                vo.set("comment",comment);
                vo.set("user",userService.selectUserById(comment.getUserId()));
                commentVos.add(vo);
            }
            model.addAttribute("comments",commentVos);
        }

        model.addAttribute("news",news);
        model.addAttribute("owner",userService.selectUserById(news.getUserId()));
        return "detail";
    }


    @RequestMapping(value = "/addComment",method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content){

        try{
            Comment comment=new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setStatus(0);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setContent(content);
            commentService.addComment(comment);
            //更新评论数量
            int count = commentService.commentCount(newsId, EntityType.ENTITY_NEWS);
            newsService.updateCount(count,newsId);
        }catch (Exception e){
            logger.error("添加评论失败！"+e.getMessage());
        }
        return "redirect:/news/"+String.valueOf(newsId);
    }
}
