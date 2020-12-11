package ru.specialist.java.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.specialist.java.spring.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {}