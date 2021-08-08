package com.baemin.server.ceo.board.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/board/**").hasRole("USER")
                .antMatchers("/board/login").permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/board/login")
                .defaultSuccessUrl("/board/news")
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .deleteCookies( "jwt-token" )
                .logoutRequestMatcher(new AntPathRequestMatcher("/board/logout"))
                .logoutSuccessUrl("/board/login")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/board/login");
    }
}
