package com.korit.servlet_study.dao;

import com.korit.servlet_study.config.DBConnectionMgr;
import com.korit.servlet_study.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthDao {
    private DBConnectionMgr dBConnectionMgr;
    private static AuthDao instance;

    private AuthDao() {
        dBConnectionMgr = DBConnectionMgr.getInstance();
    }
    public static AuthDao getInstance() {
        if (instance == null) {
            instance = new AuthDao();
        }
        return instance;
    }

    public User findUserByUserName(String username) { // 매개변수 userName
        User foundUser = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dBConnectionMgr.getConnection();
            String sql = """
                    select
                        user_id,
                        username,
                        password,
                        name,
                        email
                    from
                        user_tb
                    where
                        username = ?
                    """;
            ps = con.prepareStatement(sql); // insert하는게 아니기 때문에 재너럴 키가 필요없다.
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                foundUser = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .build();  // 단건조회이기 떄문에 while문은 사용안한다.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dBConnectionMgr.freeConnection(con,ps,rs);
        }

        return foundUser;

    }

    public User signup(User user) {
        User insertedUser = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dBConnectionMgr.getConnection();
            String sql = """
                     insert into user_tb values(default,?,?,?,?)                                       
                    """;
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 생성된 id
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys(); // RETURN_GENERATED_KEYS 이게 있어야만 사용가능
            if (rs.next()) {
                insertedUser = User.builder()
                        .userId(rs.getInt(1)) // rs는 ()이 안에 숫자로 원하는 열 선택 가능
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace(); // 어떤예외가 일어났는지 알 수 있는 코드
        } finally {
            dBConnectionMgr.freeConnection(con, ps);
        }

        return insertedUser;
    }
}
