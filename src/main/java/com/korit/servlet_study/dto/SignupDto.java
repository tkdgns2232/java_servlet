package com.korit.servlet_study.dto;

import com.korit.servlet_study.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Data
@NoArgsConstructor
@AllArgsConstructor // 생성할때 getter,setter가 있어야 json이 알아서 작성가능
public class SignupDto {
    private String username;
    private String password;
    private String name;
    private String email;

    public User toUser() {
        return User.builder()
                .username(username)
                .password(BCrypt.hashpw(password,BCrypt.gensalt(10))) // BCrypt.gensalt(10)) hash암호화를 해주는데 소괄호안에 있는 숫자가 클 수록 암호 난이도 up 10이 기본값
                .name(name)
                .email(email)
                .build();
    }

}
