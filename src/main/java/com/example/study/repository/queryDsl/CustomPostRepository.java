package com.example.study.repository.queryDsl;

import com.example.study.entity.Post;

import java.util.Optional;

public interface CustomPostRepository {
    Optional<Post> findByIdAndTitle(Long id, String title);
}
