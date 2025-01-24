package com.korit.servlet_study.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dao.UserDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.entity.User;
import com.korit.servlet_study.security.annotation.JwtValid;
import com.korit.servlet_study.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class AuthenticationFilter implements Filter {
    private JwtProvider jwtProvider;
    private UserDao userDao;

    public AuthenticationFilter() {
        jwtProvider = JwtProvider.getInstance();
        userDao = UserDao.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest; // 다운캐스팅
        HttpServletResponse response = (HttpServletResponse) servletResponse; // 다운캐스팅

        try {
            if(isJwtTokenValid(request)) {
                String bearerToken = request.getHeader("Authorization");
                if(bearerToken == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }

                Claims claims = jwtProvider.parseToken(bearerToken);
                if(claims == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }

                int userId = Integer.parseInt(claims.get("userId").toString());
                User foundUser = userDao.findById(userId);
                if(foundUser == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }
            };
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(servletRequest, servletResponse); // 다음단계 필터로 넘어가게하는 코드
    }

    private boolean isJwtTokenValid(HttpServletRequest request) throws ClassNotFoundException {
        String method = request.getMethod();
        String servletPath = request.getHttpServletMapping().getServletName();

        Class<?> servletClass = Class.forName(servletPath);
        Method foundMethod = getMappedMethod(servletClass, method);
        return foundMethod != null;
    }

    private Method getMappedMethod(Class<?> clazz, String methodName) {
        for(Method method : clazz.getDeclaredMethods()) {
            if(method.getName().toLowerCase().endsWith(methodName.toLowerCase()) && method.isAnnotationPresent(JwtValid.class)){
                // 메소드랑 http타고오는걸 소문자로 변경 isAnnotationPresent boolean형
                return method;
            }
        }
        return null;
    }

    private void setUnAuthenticatedResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto<String> responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
        response.setStatus(responseDto.getStatus());
        response.setContentType("application/json");
        response.getWriter().println(objectMapper.writeValueAsString(responseDto));

    }
}
