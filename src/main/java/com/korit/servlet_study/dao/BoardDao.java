package com.korit.servlet_study.dao;

import com.korit.servlet_study.config.DBConnectionMgr;
import com.korit.servlet_study.entity.Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class BoardDao {
    private DBConnectionMgr dBConnectionMgr;
    private static BoardDao instance;

    private BoardDao() {
        dBConnectionMgr = DBConnectionMgr.getInstance();
    }
    public static BoardDao getInstance() {
        if (instance == null) {
            instance = new BoardDao();
        }
        return instance;  // 싱글톤 패턴
    }
    // Optional<> 값이 존재할 수도 있고 아닐 수 도 있다
    public Board save(Board board) {
        Board insertedBoard = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dBConnectionMgr.getConnection(); // connection은 try catch를 꼭 한다.
            String sql = """
                     insert into board_tb values (default,?, ?)
                    """;
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 키값 코드
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.executeUpdate(); // executeUpdate는 int로 리턴 executeQuery는 Rs를 리턴
            ResultSet rs = ps.getGeneratedKeys(); // select는 ResultSet이 필요
            if (rs.next()) {
                insertedBoard = Board.builder()
                        .boardId(rs.getInt(1))
                        .title(board.getTitle())
                        .content(board.getContent())
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            dBConnectionMgr.freeConnection(con, ps);
        }
        return insertedBoard;
    }
}

