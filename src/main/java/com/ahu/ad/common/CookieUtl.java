package com.ahu.ad.common;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtl {
    public static final String COOKIE_NAME ="_user";

    public static void removeCookie(HttpServletRequest request,HttpServletResponse response){
        // 清除cookie
        Cookie[] cookies=request.getCookies();
        try{
            if(cookies!=null){
                for(Cookie cookie:cookies){
                    String   value=cookie.getName();
                    if(value.equals("_user")){
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    public static void addCookie(HttpServletResponse response,String name){
        Cookie cookie=new Cookie(COOKIE_NAME,name);
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);
        log.info("add cookie sucess");
    }
}
