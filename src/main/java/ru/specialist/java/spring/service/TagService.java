package ru.specialist.java.spring.service;

import ru.specialist.java.spring.entity.Tag;

import java.util.List;

public interface TagService {

    void createTag(String name);

    void createTags(String... names);

    List<Tag> findAll();
}