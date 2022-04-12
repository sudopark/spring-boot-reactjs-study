package com.example.demo.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = this.parseBearToken(request);
            logger.info("filter is running...");
            if(token != null && !token.equalsIgnoreCase("null")) {

                // userId 가져오기 -> 없으면 에러
                String userId = tokenProvider.validateAndGetUserId(token);
                logger.info("Authentication userId: " + userId);

                // 인증 완료, SecurityContentHolder에 등록해야 인증된 사용자라 판단
                // authentication에 사용자 인증정보 저장하고 context -> holder에 저장
                // context에 저장된 유저정보는 이후에 처리쪽에서 빼내서 처리 가능
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // SecurityContextHolder는 ThreadLocal에 저장됨 => thread마다 하나의 context 관리 및 같은 스레드라면 어디서든 접근 가능
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            logger.error("could not set user authentication in security context: ", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearToken(HttpServletRequest request) {
        // http 요청 헤더를 파싱하여 beartoken 획득
        String bearToken = request.getHeader("Authorization");

        // StringUtils.hasText => 실제 텍스트인지(white space 로만 이루어진거는 아닌지 등 검사)
        if(StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")) {
            return bearToken.substring(7);
        }
        return null;
    }
}
