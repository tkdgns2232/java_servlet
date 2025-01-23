package com.korit.servlet_study.service;

import com.korit.servlet_study.dao.AuthDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SigninDto;
import com.korit.servlet_study.dto.SignupDto;
import com.korit.servlet_study.entity.User;
import com.korit.servlet_study.security.jwt.JwtProvider;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private AuthDao authDao;
    private JwtProvider jwtProvider;

    private static AuthService instance;

    private AuthService() {
        authDao = AuthDao.getInstance();
        jwtProvider = JwtProvider.getInstance();
    }
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    } // 싱글톤 코드


    public ResponseDto<?> signup(SignupDto signupDto) { // Dto를 entity로 변환해줘야한다.
        User user = signupDto.toUser();
        User insertedUser = authDao.signup(user);
        if (insertedUser == null) {
            return ResponseDto.fail("회원가입 실패!");
        }
        return ResponseDto.success(insertedUser);
    }

    public ResponseDto<?> signin(SigninDto signinDto) {
        User foundUser = authDao.findUserByUserName(signinDto.getUsername());
        if (foundUser == null) {
            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        if(!BCrypt.checkpw(signinDto.getPassword(), foundUser.getPassword())) { // 로그인한 비밀번호와 암호화된 비밀번호가 같은지 확인
            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        return ResponseDto.success(jwtProvider.generateToken(foundUser));

    }
}
