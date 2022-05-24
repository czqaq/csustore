package org.csu.store.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = "localhost";
    private final static String COOKIE_NAME = "csustore_token";

    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        log.info("Write CookieName:{}, CookieValue:{}", COOKIE_NAME, token);

        response.addCookie(cookie);
    }

//    public static String readLoginToken(HttpServletRequest request){
//
//    }
//
//    public static void delete(HttpServletRequest request){
//
//    }

}
