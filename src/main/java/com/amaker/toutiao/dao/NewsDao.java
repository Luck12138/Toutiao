package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    String SELECT_NAME="id,title,link,image,like_count,comment_count,created_date,user_id";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_NAME,")" +
            " values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select",SELECT_NAME,"from",TABLE_NAME,"where id=#{id}"})
    News selectNewsById(int id);

    @Update({"update",TABLE_NAME,"set comment_count=#{commentCount} where id=#{id}"})
    void updateCount(@Param("commentCount") int commentCount,
                     @Param("id") int id);

    List<News> selectNewsByUserIdAndOffset(@Param("userId") int userId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);
}
