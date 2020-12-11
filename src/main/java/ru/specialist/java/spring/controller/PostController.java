package ru.specialist.java.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.entity.Comment;
import ru.specialist.java.spring.service.CommentService;
import ru.specialist.java.spring.service.PostService;
import ru.specialist.java.spring.service.TagService;
import ru.specialist.java.spring.service.UserService;


@Controller
public class PostController {

    private final PostService postService;
    private final TagService tagService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService,
                          TagService tagService,
                          UserService userService,
                          CommentService commentService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap,
                        @RequestParam(required = false) String search) {
        if (search != null) {
            modelMap.put("posts", postService.search(search));
            modelMap.put("title", "Search by");
            modelMap.put("subtitle", search.length() < 20
                    ? search
                    : search.substring(20) + "...");
        } else {
            modelMap.put("posts", postService.listAllPosts());
            modelMap.put("title", "All posts");
        }

        setCommonParams(modelMap);
        return "blog";
    }

    @GetMapping("/tag/{tagName}")
    public String tag(@PathVariable String tagName, ModelMap modelMap) {
        modelMap.put("posts", postService.findByTag(tagName));
        modelMap.put("title", "Posts by tag");
        modelMap.put("subtitle", "#" + tagName);
        setCommonParams(modelMap);
        return "blog";
    }

    @GetMapping("/user/{username}")
    public String user(@PathVariable String username, ModelMap modelMap) {
        modelMap.put("posts", postService.findByUser(username));
        modelMap.put("title", "Posts by");
        modelMap.put("subtitle", username);
        setCommonParams(modelMap);
        return "blog";
    }

    @GetMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "post-new";
    }

    @PostMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(PostDto postDto) {
        postService.createPost(postDto);
        return "redirect:/";
    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap, @PathVariable long postId) {
        postService.checkAuthority(postId);

        modelMap.put("post", postService.getAsDto(postId));
        setCommonParams(modelMap);
        return "post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(PostDto postDto) {
        postService.checkAuthority(postDto.getPostId());
        postService.update(postDto);
        return "redirect:/";
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("tags", tagService.findAll());
        modelMap.put("users", userService.findAll());
    }

    @GetMapping("/post/view/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String postView(ModelMap modelMap, @PathVariable long postId) {
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        modelMap.put("comments", commentService.allList(postId));
        return "post-view";
    }

    @PostMapping("/post/view/commit")
    @PreAuthorize("hasRole('USER')")
    public String addCommit(ModelMap modelMap, @PathVariable long postId, Comment comment){
        commentService.creatNewComment(comment);
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        modelMap.put("comments", commentService.allList(postId));
        return "post-view";
    }
}