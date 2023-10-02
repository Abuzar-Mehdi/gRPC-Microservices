package com.learning.microservices.grpc.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("request = " + request.getHeader("Authorization"));
        System.out.println("request = " + request.getSession().getId());
        System.out.println("request = " + request.getServletPath());


        return request.getHeader("Authorization").startsWith("Bearer");
    }
}
