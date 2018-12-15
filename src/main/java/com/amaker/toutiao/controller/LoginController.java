package com.amaker.toutiao.controller;

import com.amaker.toutiao.service.UserService;
import com.amaker.toutiao.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: toutiao
 * @Date: 2018/12/13 0013 14:01
 * @Author: GHH
 * @Description:
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/reg/",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(Model model,
                        @RequestParam( "username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember",defaultValue ="0") int rember){

        try {
            Map<String,Object> map=userService.register(username,password);

            if(map.isEmpty()){
                return TouTiaoUtil.getJsonString(0,"注册成功");
            }
            else {
                return TouTiaoUtil.getJsonString(1,map);
            }


        }catch (Exception e) {
            logger.error("注册异常"+e.getMessage());
            return TouTiaoUtil.getJsonString(1,"注册异常");
        }

    }


    @RequestMapping(value = "/login/",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember",defaultValue = "0") int rember,
                        HttpServletResponse response){


        try {
            Map<String,Object> map=userService.login(username,password);

            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rember>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return TouTiaoUtil.getJsonString(0,"登录成功");
            }
            else {
                return TouTiaoUtil.getJsonString(1,map);
            }


        }catch (Exception e) {
            logger.error("登录异常"+e.getMessage());
            return TouTiaoUtil.getJsonString(1,"登录异常");
        }

    }
}
