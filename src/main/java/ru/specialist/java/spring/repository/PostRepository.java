package ru.specialist.java.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.specialist.java.spring.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitle(String title);

    List<Post> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT p.* FROM post AS p JOIN post_tag AS pt ON p.post_id = pt.post_id JOIN tag AS t ON (pt.tag_id = t.tag_id AND LOWER(t.name) = LOWER(?)) ORDER BY p.created_at DESC", nativeQuery = true)
    List<Post> findByTagName(String tagName);

    //1) Найти посты, содержащие в тексте(content) заданную подстроку (оператор like в SQL)
    List<Post> findByContentLikeIgnoreCase(String substring);

    //2) Получить все посты, отсортированные по количеству тегов
    @Query(value = "SELECT p.* FROM post AS p JOIN post_tag AS pt ON p.post_id = pt.post_id GROUP BY p.post_id ORDER BY COUNT(*) DESC", nativeQuery = true)
    List<Post> findSortedTagSorted();
}