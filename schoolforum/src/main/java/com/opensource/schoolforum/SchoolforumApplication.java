package com.opensource.schoolforum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan(basePackages ="com.opensource.schoolforum.mapper")
public class SchoolforumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolforumApplication.class, args);
    }

}
