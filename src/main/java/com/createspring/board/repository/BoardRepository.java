package com.createspring.board.repository;

import com.createspring.ConnectionUtil;
import com.createspring.board.entity.Post;
import com.createspring.spring.annotation.Repository;
import com.createspring.spring.annotation.Transactional;

import java.sql.*;
import java.util.NoSuchElementException;


@Repository
public class BoardRepository {

    //TODO 오토커밋을 제거하고 트랜잭셔널을 붙여보자
    /**
     * 같은 글을 2개 연속 저장함 트랜잭션이 있다면 모두 롤백 없다면 하나가 남아있음
     */
    @Transactional
    public void save(Post p) {
        System.out.println("인스턴스" + this);
        String sql = "insert into post(id, title, content, author) values(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionUtil.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, p.getId());
            pstmt.setString(2, p.getTitle());
            pstmt.setString(3, p.getContent());
            pstmt.setString(4, p.getAuthor());
            pstmt.executeUpdate();
            pstmt.executeUpdate();
            con.commit();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("롤백 실패");
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.close(con, pstmt, null);
        }
    }


    public Post findById(Long id) {
        System.out.println("인스턴스" + this);
        String sql = "select * from post where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionUtil.getConnection();
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
            System.out.println("돔황챠!!!!!! 커넥션이 터졌다!!!!!");
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.close(con, pstmt, null);
        }
    }
}
