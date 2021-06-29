package com.example.demo.pojo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class HelloWorld {
    @ResponseBody
    @RequestMapping("/hello")
    public  String sayHello(){

        return  "sayHello";
    }
}
