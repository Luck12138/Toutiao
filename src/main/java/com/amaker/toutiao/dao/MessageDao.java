package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/20 0020 21:06
 * @Author: GHH
 * @Description:
 */
@Component
public interface MessageDao {
    String TABLE_NAME="message";
    String INSERT_FIELDS=" from_id,to_id,content,created_date,has_read,conversation_id";
    String SELECT_FIELDS="id,from_id,to_id,content,created_date,has_read,conversation_id";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    void addMessage(Message message);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where conversation_id=#{conversationId}  order by id desc limit #{offset},#{limit}"})
    List<Message> selectMessageByConId(@Param("conversationId") String conversationId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"select count(id) from message where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationCount(@Param("userId") int userId,
                             @Param("conversationId") String conversationId);

    @Select({"select",INSERT_FIELDS,", count(id) as id from (select * from", TABLE_NAME,
            "where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

}
