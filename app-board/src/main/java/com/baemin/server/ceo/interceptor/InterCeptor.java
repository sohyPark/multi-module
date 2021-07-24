package com.baemin.server.ceo.interceptor;

import com.baemin.server.ceo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterCeptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        if ( requestURI.indexOf("/api") != 0 ) {
            return false;
        }

        final String token = request.getHeader( "jwt-auth-token" );
        if ( !tokenService.verifyToken( token ) ) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"isToken\":\"false\"}");
            return false;
        }

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
