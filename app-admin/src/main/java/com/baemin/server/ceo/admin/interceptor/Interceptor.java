package com.baemin.server.ceo.admin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        if ( requestURI.indexOf("/api") != 0 ) {
            return false;
        }

        final String token = request.getHeader( "jwt-auth-token" );

        String requestId;
        if ( "/api/login".equals( requestURI ) ) {
            requestId = request.getParameter( "id" );
            if ( null == requestId ) {
                return false;
            }
        }
        return true;
    }
}
