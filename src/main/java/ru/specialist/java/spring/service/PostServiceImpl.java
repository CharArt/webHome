package ru.specialist.java.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.entity.Post;
import ru.specialist.java.spring.entity.Tag;
import ru.specialist.java.spring.repository.PostRepository;
import ru.specialist.java.spring.repository.TagRepository;
import ru.specialist.java.spring.repository.UserRepository;
import ru.specialist.java.spring.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Post> listAllPosts() {
        return postRepository.findAll(Sort.by("createdAt").descending());
    }

    //    add ordering
    @Override
    public List<Post> search(String search) {
        return postRepository.findByContentLikeIgnoreCase("%" + search + "%");
    }

    //     add ordering
    @Override
    public List<Post> findByTag(String tagName) {
        return postRepository.findByTagName(tagName);
    }

    //     add ordering
    @Override
    public List<Post> findByUser(String username) {
        List<Post> posts = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)).getPosts();
        posts.size();
        return posts;
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTags(parseTags(postDto.getTags()));
        post.setCreatedAt(LocalDateTime.now());

        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        post.setUser(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)));
        postRepository.save(post);
    }

    @Override
    public void checkAuthority(long postId) {
        SecurityUtils.checkAuthority(postRepository.findById(postId).orElseThrow().getUser().getUsername());
    }

    @Override
    public PostDto getAsDto(long postId) {
        return toDto(postRepository.findById(postId).orElseThrow());
    }

    @Override
    public void update(PostDto postDto) {
        Post post = postRepository.findById(postDto.getPostId()).orElseThrow();
//      апдейт постов не работал из-за отсутствия отрицания ниже
        if (!StringUtils.isEmpty(postDto.getTitle()))
            post.setTitle(postDto.getTitle());
        if (!StringUtils.isEmpty(postDto.getContent()))
            post.setContent(postDto.getContent());
        if (!StringUtils.isEmpty(postDto.getTags()))
            post.setTags(parseTags(postDto.getTags()));
        postRepository.save(post);
    }

    private PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setTags(post.getTags().stream().map(Tag::getName).collect(Collectors.joining(" ")));
        return dto;
    }

    private List<Tag> parseTags(String tags) {
        return Arrays.stream(tags.split(" ")).map(tagName -> tagRepository.save(new Tag(tagName))) .collect(Collectors.toList());
    }
}
