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
public class SignupRestServlet extends HttpServlet {

    private AuthService authService;

    public SignupRestServlet() {
        authService = AuthService.getInstance();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder(); // StringBuilder 문자열을 합쳐준다

        try (BufferedReader bufferedReader = req.getReader()) { // buffer
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SignupDto signupDto = objectMapper.readValue(stringBuilder.toString(), SignupDto.class);  // json은 키,밸류로 받는다.
        // 파라미터로 전달 x


        ResponseDto<?> responseDto = authService.signup(signupDto);
        String responseJson = objectMapper.writeValueAsString(responseDto);

        resp.setStatus(responseDto.getStatus());
        resp.setContentType("application/json");
        resp.getWriter().println(responseJson);
    }
}
