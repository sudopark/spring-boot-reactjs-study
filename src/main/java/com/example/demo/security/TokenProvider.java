package com.example.demo.security;

import com.example.demo.persistence.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "NMA8JPctFuna59f5";

    public String create(UserEntity userEntity) {

        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기위한 SECRET_KET => 여기서는 대칭 알고리즘 사용
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws method로 Base64 디코딩 및 파싱
        // 헤더와 페이로드를 setStringKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claim) 리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId가 핑요해 getBody를 호출할거임

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
