package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.NewsDao;
import com.amaker.toutiao.model.News;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}


