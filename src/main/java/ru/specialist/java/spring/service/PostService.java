package ru.specialist.java.spring.service;

import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> listAllPosts();

    List<Post> search(String search);

    List<Post> findByTag(String tagName);

    List<Post> findByUser(String username);

    void createPost(PostDto postDto);

    void checkAuthority(long postId);

    PostDto getAsDto(long postId);

    void update(PostDto postDto);
}
