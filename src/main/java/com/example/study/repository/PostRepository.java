package com.example.study.repository;

import com.example.study.entity.Post;
import com.example.study.repository.queryDsl.CustomPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

    Optional<Post> findByIdAndTitle(Long id, String title);
}
