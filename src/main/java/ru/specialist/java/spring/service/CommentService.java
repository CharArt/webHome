package ru.specialist.java.spring.service;

import ru.specialist.java.spring.entity.Comment;
import ru.specialist.java.spring.entity.User;

import java.util.List;

public interface CommentService {

    public List<Comment> allList(long post_id);

    void creatNewComment(Comment comment);

}
