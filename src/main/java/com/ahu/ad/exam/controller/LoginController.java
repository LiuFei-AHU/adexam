package com.ahu.ad.exam.controller;

import com.ahu.ad.common.CookieUtl;
import com.ahu.ad.common.response.Result;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.service.LoginService;
import com.ahu.ad.exam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public Object login(HttpServletRequest req,@CookieValue(value = CookieUtl.COOKIE_NAME,defaultValue = "") String username){
        if(StringUtils.isNotEmpty(username)){
            log.info("get cookie");
            UserVo user = loginService.getUser(username);
            req.getSession().setAttribute("user",user);
            return new ModelAndView("redirect:/home");
        }
        if(req.getSession().getAttribute("user")!=null){
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, String username, String password, String rememberMe){
        //log.info(username+" "+password);
        Result<UserVo> result = null;
        try{
            result = loginService.login(username,password);
            if(result.getData() != null){
                request.getSession().setAttribute("user",result.getData());
                if(StringUtils.isNotEmpty(rememberMe) &&  rememberMe.equals("Y")){
                    CookieUtl.addCookie(response,username);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            result = new Result<>();
            result.setResultCode("101");
            result.setSuccess(false);
            result.setResultMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping("/register")
    @Transactional
    public Result<Object> register(HttpServletRequest request,HttpServletResponse response,User user){
        //log.info(user.toString());
        Result<Object> result = new Result<Object>();
        try{
            UserVo user1 = loginService.getUser(user.getUsername());
            if(user1!=null && user1.getId()!=null){
                result.setResultCode("101");
                result.setSuccess(false);
                result.setResultMessage("用户名已存在");
                return result;
            }
            loginService.register(user);
            result.setResultCode("100");
            result.setSuccess(true);
            CookieUtl.removeCookie(request,response);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            result.setResultCode("101");
            result.setSuccess(false);
            result.setResultMessage(e.getMessage());
        }
        return result;
    }

    @GetMapping("/register")
    public Object register(){
        return new ModelAndView("register");
    }

    @GetMapping("/logout")
    public Object logout(HttpServletRequest request,HttpServletResponse response){
        CookieUtl.removeCookie(request,response);
        request.getSession().invalidate();
        return new ModelAndView("redirect:/login");
    }

}
