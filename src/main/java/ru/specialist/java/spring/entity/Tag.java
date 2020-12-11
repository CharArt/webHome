package ru.specialist.java.spring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    @Column(name = "name")
    private String name;

    public Tag() {}
    public Tag(String name) { this.name = name;}

    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}