package com.post_hub.iam_service.utils;

import com.post_hub.iam_service.model.constants.ApiConstants;
import jakarta.servlet.http.Cookie;

import org.springframework.http.HttpHeaders;

public class ApiUtils {

    public static String getMethodName(){
        try{
            return Thread.currentThread().getStackTrace()[1].getMethodName();
        } catch (Exception cause){

                return ApiConstants.UNDEFINED;
        }
    }

    public static Cookie createAuthCookie(String value){
        Cookie authorizationCookie = new Cookie(HttpHeaders.AUTHORIZATION,value);
        authorizationCookie.setHttpOnly(true);
        authorizationCookie.setSecure(true);
        authorizationCookie.setPath("/");
        authorizationCookie.setMaxAge(300);
        return authorizationCookie;

    }

}
