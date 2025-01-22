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

    public User signup(User user) {
        User insertedUser = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dBConnectionMgr.getConnection();
            String sql = """
                     insert into user_tb values(default,?,?,?,?)                                       
                    """;
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedUser = User.builder()
                        .userId(rs.getInt(1))
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            dBConnectionMgr.freeConnection(con, ps);
        }

        return insertedUser;
    }
}
