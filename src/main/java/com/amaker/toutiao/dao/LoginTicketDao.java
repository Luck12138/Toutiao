package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.Insert;
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
    String SELECT_FIELDS="id "+INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values(#{userId},#{ticket},#{expired},#{status})"})
    public void insert(LoginTicket loginTicket);


}
