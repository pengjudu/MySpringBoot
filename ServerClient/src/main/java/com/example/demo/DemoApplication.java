package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example")
@SpringBootApplication
@EnableAutoConfiguration
// 重点
@ServletComponentScan
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run (DemoApplication.class, args);
    }

}
