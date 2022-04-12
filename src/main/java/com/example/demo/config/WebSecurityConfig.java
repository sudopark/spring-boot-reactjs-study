package com.example.demo.config;


import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors() // WebMvcConfig에서 이미 설정했기때문에 기본 cors 설정
            .and()
            .csrf() // csrf는 현재 사용 안하므로 disable
                .disable()
            .httpBasic()// token 인증을 이용하므로 basic 인증은 disable
                .disable()
            .sessionManagement()    // Session 기반이 아님을 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()// /와 /auth 경로는 인증 안해도됨
                .antMatchers("/", "/auth/**").permitAll()
            .anyRequest()// /와 /auth 제외 모두 인증필요
                .authenticated();

        // filter 등록 -> 매 요청마마 CorsFilter를 실행한 이후에(두번째가 afterFilter)
        // jwtAuthenticationFilter를 실행한다
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }
}
