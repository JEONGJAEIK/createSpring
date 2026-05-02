package com.createspring.board;

import com.createspring.board.entity.Post;
import com.createspring.spring.annotation.Repository;
import com.createspring.spring.jdbc.DataSourceUtils;

import java.sql.*;
import java.util.NoSuchElementException;


@Repository
public class BoardRepository {

    private final ConnectionUtil connectionUtil;

    public BoardRepository(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    /**
     * 글 저장
     */
    public void save(Post p) {
        String sql = "insert into post(id, title, content, author) values(?, ?, ?, ?)";
        try {
            Connection con = DataSourceUtils.getConnection(connectionUtil);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, p.getId());
            pstmt.setString(2, p.getTitle());
            pstmt.setString(3, p.getContent());
            pstmt.setString(4, p.getAuthor());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("쿼리 망했다 돔항챠아아아앜");
            throw new RuntimeException(e);
        }
    }


    public Post findById(Long id) {
        String sql = "select * from post where id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs;

        try {
            Connection con = DataSourceUtils.getConnection(connectionUtil);
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Post(rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"));

            } else {
                throw new NoSuchElementException("결과가 없당께");
            }
        } catch (SQLException e) {
            System.out.println("예외 발생");
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.close(pstmt, null);
        }
    }
}
