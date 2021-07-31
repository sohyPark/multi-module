package com.baemin.server.ceo.board.config;

import com.baemin.server.ceo.board.security.JwtTokenProvier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvier jwtTokenProvier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        if ( requestURI.indexOf("/api") != 0 ) {
            return false;
        }

        final String token = request.getHeader( "jwt-auth-token" );
        if ( !jwtTokenProvier.verifyToken( token ) ) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"isToken\":\"false\"}");
            return false;
        }

        String requestId;
        if ( "/api/login".equals( requestURI ) ) {
            requestId = request.getParameter( "email" );
            if ( null == requestId ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
