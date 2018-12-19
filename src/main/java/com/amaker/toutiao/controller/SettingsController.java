package com.amaker.toutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: toutiao
 * @Date: 2018/12/17 0017 13:16
 * @Author: GHH
 * @Description:
 */
@Controller
public class SettingsController {

    @RequestMapping("/setting")
    public String settings(){

        return "header";
    }
}
