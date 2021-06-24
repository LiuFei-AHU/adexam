package com.ahu.ad.exam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
@RequestMapping("/home")
public class HomeController {

    @GetMapping("")
    public Object home(HttpServletRequest req, ModelMap map){
        map.addAttribute("user",req.getSession().getAttribute("user"));
        return new ModelAndView("home");
    }

    @GetMapping("/welcome")
    public Object welcome(){
        return new ModelAndView("welcome");
    }

    @GetMapping("/main")
    public Object main(){
        return new ModelAndView("exam/main");
    }

    @GetMapping("/download")
    public Object download(){
        return new ModelAndView("exam/download");
    }

}
