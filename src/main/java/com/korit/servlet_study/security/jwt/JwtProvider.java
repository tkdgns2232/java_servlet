package com.korit.servlet_study.security.jwt;

import com.korit.servlet_study.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtProvider {

    private Key key;
    private static JwtProvider instance;

    private JwtProvider() {
        final String SECRET = "db7cb0b812398f518687fa51f888d8c5c612cce3cee4a5a7e2b18d398786b65af763add57ae60c10aee931a2b8a6977273ab2b982daa7cc2321aef7910f24d302f8e260f48c206f71865c564e7c9f218e0a616f139215a348499a0f4df2b4975e1daff3f5ce27faf5fdf95402dda33c4851b93fab9ceb2ce2409724eb9abfc8b49f390392ad586e87a59914e01117ae041a74534c789ebd2e778cdf041363fd17ed4188fafa9dad9ceab06e363189a098acc486ca21da9f0a4501fa70732523ae0b209d38f487dcf3650847e92187f6d08278a4a547f60060ca4f377f2b524501f05fc9bb73d85f117d6b1063f299425fb736efef54d7309b4b09e58df9bd6d6";
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)); //Jwt만드는 문법
    }

    public static JwtProvider getInstance() {
        if (instance == null) {
            instance = new JwtProvider();
        }
        return instance;
    }

    private Date getExpiryDate() {
        return new Date(new Date().getTime() + (1000L * 60 * 60 * 24 * 365)); // L은 long타입 앞에서부터 1초 L을 안쓰면 int 타입 초과
    }

    public String generateToken(User user) { // 로그인 되어진 User 객체를 가지고 온다.
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .setExpiration(getExpiryDate()) // 토큰은 만료시간을 정해줘야 한다
                .signWith(key, SignatureAlgorithm.HS256) // 256으로 만들었으니 HS256 사용
                .compact(); // .compact()를 생성하면 토큰이 만들어진다
    } // 토큰 생성 코드

    public Claims parseToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(removeBearer(token))
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims; // 파싱해주는 코드

    }

    private String removeBearer(String bearerToken) {
        String accessToken = null;
        final String BEARER_KEYWORD = "Bearer ";
        if (bearerToken.startsWith(BEARER_KEYWORD)) {
            accessToken = bearerToken.substring(BEARER_KEYWORD.length()); // substring은 문자열 자르기
        }
        return accessToken;
    }
}
