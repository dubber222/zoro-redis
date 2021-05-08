package com.zoro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
/*@EnableTransactionManagement 可以省略不要*/
public class ZoroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoroApplication.class, args);
    }

}
