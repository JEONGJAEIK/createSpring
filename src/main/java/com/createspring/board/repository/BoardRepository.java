package com.createspring.board.repository;

import com.createspring.board.entity.Post;
import com.createspring.spring.annotation.Repository;
import com.createspring.spring.annotation.Transactional;

import java.sql.*;
import java.util.NoSuchElementException;


@Repository
public class BoardRepository {

    /**
     * 같은 글을 2개 연속 저장함 트랜잭션이 있다면 모두 롤백 없다면 하나가 남아있음
     */
    @Transactional
    public void save(Post p) {
        String sql = "insert into post(id, title, content, author) values(?, ?, ?, ?)";
        try {
            Connection con = ConnectionUtil.getConnection();
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
            ConnectionUtil.close(pstmt, null);
        }
    }
}
