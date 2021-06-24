package com.ahu.ad.common.interceptor;

import com.ahu.ad.common.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author feiliu6
 * @Date 16:23 2021/6/6
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, HttpServletResponse response,Exception ex){
        if(!(ex instanceof MyException)){
//            log.error(ex.getMessage());
            ex.printStackTrace();
            ex = new MyException("301",MyException.ERROR,"系统异常，请联系管理员！");
        }
        request.setAttribute("ex",ex);
        return new ModelAndView("exception");
    }
}