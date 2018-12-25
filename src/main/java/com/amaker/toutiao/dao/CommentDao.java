package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/19 0019 20:47
 * @Author: GHH
 * @Description:
 */
@Component
public interface CommentDao {

    String TABLE_NAME="comment ";
    String INSERT_FIELDS=" user_id, entity_id, entity_type, created_date, status,content";
    String SELECT_FIELDS="id,user_id, entity_id, entity_type, created_date, status,content ";


    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{userId},#{entityId},#{entityType},#{createdDate},#{status},#{content})"})
    void addComment(Comment comment);

    @Select({"select ",SELECT_FIELDS," from",TABLE_NAME,
            "where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByIdAndType(@Param("entityId") int entityId,
                                           @Param("entityType") int entityType);

    @Select({"select count(id) from",TABLE_NAME,
            " where  entity_id=#{entityId} and entity_type=#{entityType}"})
    int commentCount(@Param("entityId") int entityId,
                     @Param("entityType") int entityType);

    @Update({"update",TABLE_NAME,"set status=#{status} where id=#{id}"})
    void updateStatus(@Param("status") int status,
                      @Param("id") int id);


}
