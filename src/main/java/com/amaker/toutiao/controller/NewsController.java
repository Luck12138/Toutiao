package com.amaker.toutiao.controller;

import com.amaker.toutiao.model.HostHolder;
import com.amaker.toutiao.model.News;
import com.amaker.toutiao.service.NewsService;
import com.amaker.toutiao.service.QiniuService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

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
    private QiniuService qiniuService;

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
}
