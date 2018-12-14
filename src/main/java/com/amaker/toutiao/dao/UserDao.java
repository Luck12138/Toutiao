package com.amaker.toutiao.dao;

import com.amaker.toutiao.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @program: toutiao
 * @Date: 2018/12/10 0010 19:03
 * @Author: GHH
 * @Description:
 */
@Component
public interface UserDao {

    String TABLE_NAME="user";
    String INSERT_FIELDS="name, password, salt, head_url ";
    String SELECT_FIELDS="id,name,password,salt,head_url";


    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    public int addUser(User user);


    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);


    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);


    @Update({"update", TABLE_NAME , "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from", TABLE_NAME ,"where id=#{id}"})
    void deleteById(int id);
}