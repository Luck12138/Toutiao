package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @program: toutiao
 * @Date: 2018/12/13 0013 17:19
 * @Author: GHH
 * @Description:
 */
@Component
public interface LoginTicketDao {

    String TABLE_NAME="login_ticket";
    String INSERT_FIELDS="user_id,ticket,expired,status";
    String SELECT_FIELDS="id,user_id,ticket,expired,status";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values(#{userId},#{ticket},#{expired},#{status})"})
    void addLoginTicket(LoginTicket loginTicket);

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME,"where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update",TABLE_NAME,"set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,
                      @Param("status") int status);

}
