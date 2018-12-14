package com.amaker.toutiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.amaker.toutiao.dao")
public class ToutiaoApplication {

    public static void main(String[] args) {


        SpringApplication.run(ToutiaoApplication.class, args);
    }
}
