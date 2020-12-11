package ru.specialist.java.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.specialist.java.spring.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment AS c INNER JOIN post AS p ON c.post_id=p.post_id WHERE p.post_id = ?;", nativeQuery = true)
    List<Comment> allListCommentPost(long post_id);
}
