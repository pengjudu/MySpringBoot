package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping("/home")
public class IndexController {
    @ResponseBody
    @RequestMapping("/login")
    public ModelAndView index(ModelAndView mav, Model model){
        mav.setViewName("index");
        return mav;

    }
}
