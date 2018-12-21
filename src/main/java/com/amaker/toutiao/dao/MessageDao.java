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

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where conversation_id=conversationId "})
    List<Message> selectMessageByConId(@Param("conversationId") String conversationId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);



}
