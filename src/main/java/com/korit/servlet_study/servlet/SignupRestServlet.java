package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.InsertBoardDto;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SignupDto;
import com.korit.servlet_study.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/signup")
public class SignupRestServlet extends HttpServlet { // HttpServlet으로 받아야한다.

    private AuthService authService;

    public SignupRestServlet() {
        authService = AuthService.getInstance();
    } // 싱글톤 생성해주는 코드

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder requestJsonData = new StringBuilder(); // StringBuilder 문자열을 합쳐준다

        try (BufferedReader bufferedReader = req.getReader()) { // buffer
            String line;
            while ((line = bufferedReader.readLine()) != null) { // 여러줄이면 while 아니면 라인하나만 해도 된다.
                requestJsonData.append(line);
            }
        } // 한줄한줄된 문자열들을 합쳐주는 코드

        ObjectMapper objectMapper = new ObjectMapper();
        SignupDto signupDto = objectMapper.readValue(requestJsonData.toString(), SignupDto.class);  // json객체를 signupDto로 변환 할 수 있다.
        // 파라미터로 전달 x


        ResponseDto<?> responseDto = authService.signup(signupDto); // 리턴으로 ResponseDto 받기위해 작성
        String responseJson = objectMapper.writeValueAsString(responseDto);

        resp.setStatus(responseDto.getStatus()); // 200인지 400인지 구분 가능하게 해주는 코드
        resp.setContentType("application/json");
        resp.getWriter().println(responseJson); // 응답해주는 코드
    }
}
