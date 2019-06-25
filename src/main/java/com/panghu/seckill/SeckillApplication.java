package com.panghu.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author panghu
 */
@MapperScan(basePackages = {"com.panghu.seckill.mapper"},sqlSessionFactoryRef = "sqlSessionFactory")
@SpringBootApplication
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }

}
