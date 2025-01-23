package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/authenticated")
public class AuthenticatedServlet  extends HttpServlet{
    private JwtProvider jwtProvider;

    public AuthenticatedServlet() {
        jwtProvider = JwtProvider.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String bearerToken = req.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto responseDto = null;

       if (bearerToken == null) {
           responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
           resp.setStatus(responseDto.getStatus());
           resp.setContentType("application/json");
           resp.getWriter().println(objectMapper.writeValueAsString(responseDto));
           return;
       }

       Claims claims = jwtProvider.parseToken(bearerToken);
       if (claims == null) {
           responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
           resp.setStatus(responseDto.getStatus());
           resp.setContentType("application/json");
           resp.getWriter().println(objectMapper.writeValueAsString(responseDto));
           return;
       }
       responseDto = ResponseDto.success(claims.get("userId"));
       resp.setStatus(responseDto.getStatus());
       resp.setContentType("application/json");
       resp.getWriter().println(objectMapper.writeValueAsString(responseDto));





    }
}
