package com.baemin.server.ceo.board.config;

import com.baemin.server.ceo.board.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterCeptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        if ( requestURI.indexOf("/board") != 0 || requestURI.indexOf("/admin") != 0 ) {
            return false;
        }
//
        final String token = request.getHeader( "jwt-auth-token" );
        if ( !jwtTokenProvider.verifyToken( token ) ) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"isToken\":\"false\"}");
            return false;
        }

        String requestEmail;
        if ( "/board/login".equals( requestURI ) ) {
            requestEmail = request.getParameter( "email" );
            return !ObjectUtils.isEmpty( requestEmail );
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
