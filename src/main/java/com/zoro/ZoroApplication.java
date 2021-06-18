package com.zoro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableTransactionManagement 可以省略不要*/
public class ZoroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoroApplication.class, args);
    }

}
