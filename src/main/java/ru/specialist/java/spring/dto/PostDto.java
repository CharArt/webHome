package ru.specialist.java.spring.dto;

public class PostDto {

    private long postId;
    private String title;
    private String content;
    private String tags;

    public long getPostId() { return postId; }
    public void setPostId(long postId) { this.postId = postId; }

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTags() { return tags;}
    public void setTags(String tags) { this.tags = tags; }
}