package com.korit.servlet_study.service;

import com.korit.servlet_study.dao.AuthDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SignupDto;
import com.korit.servlet_study.entity.User;

public class AuthService {

    private AuthDao authDao;

    private static AuthService instance;

    private AuthService() {
        authDao = AuthDao.getInstance();
    }
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }


    public ResponseDto<?> signup(SignupDto signupDto) {
        User user = signupDto.toUser();
        User insertedAuth = authDao.signup(user);
        if (insertedAuth == null) {
            return ResponseDto.fail("회원가입 실패!");
        }
        return ResponseDto.success(insertedAuth);
    }
}
