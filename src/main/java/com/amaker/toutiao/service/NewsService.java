package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.NewsDao;
import com.amaker.toutiao.model.News;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * @program: toutiao
 * @Date: 2018/12/11 0011 13:20
 * @Author: GHH
 * @Description:
 */
@Service
public class NewsService {

        @Autowired
         private NewsDao newsDao;

        public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectNewsByUserIdAndOffset(userId,offset,limit);
        }
        public String saveImage(MultipartFile file)throws IOException {

            int doPos = file.getOriginalFilename().lastIndexOf(".");

            if(doPos<0){
                return null;
            }
            String fileExt=file.getOriginalFilename().substring(doPos+1).toLowerCase();
            if(!TouTiaoUtil.isFileAllowed(fileExt)){
                return null;
            }

            String fileName=UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
            Files.copy(file.getInputStream(),new File(TouTiaoUtil.IMG_DIR+fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return  TouTiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;
        }
}


