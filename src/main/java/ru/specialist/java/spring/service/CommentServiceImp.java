package ru.specialist.java.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.specialist.java.spring.entity.Comment;
import ru.specialist.java.spring.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) { this.commentRepository = commentRepository; }

    @Override
    public List<Comment> allList(long post_id) {
        return commentRepository.allListCommentPost(post_id);
    }

    @Override
    public void creatNewComment(Comment comment) {
        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setPost(comment.getPost().);
        commentRepository.save(newComment);
    }

}