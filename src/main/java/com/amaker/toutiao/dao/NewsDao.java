package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/11 0011 12:22
 * @Author: GHH
 * @Description:
 */

@Component
public interface NewsDao {
    String TABLE_NAME="news";
    String INSERT_NAME="title,link,image,like_count,comment_count,created_date,user_id";
    String SELECT_NAME="id "+INSERT_NAME;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_NAME,")" +
            " values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    List<News> selectNewsByUserIdAndOffset(@Param("userId") int userId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);
}
