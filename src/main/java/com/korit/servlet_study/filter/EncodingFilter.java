package com.korit.servlet_study.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 동작!!");
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        servletRequest.setCharacterEncoding("UTF-8"); // req 한국어 설정하는 코드
        servletResponse.setCharacterEncoding("UTF-8");  //  resp 한국어 설정하는 코드
        // 서블릿 전, 후 가 존재한다
        filterChain.doFilter(servletRequest, servletResponse); // 이 부분이 doget

        System.out.println("후처리"); // 여기가 서블릿 후
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
