package com.ahu.ad.exam.controller;

import com.ahu.ad.exam.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/manage")
    public Object manage(){
        return new ModelAndView("role/manage");
    }



}
