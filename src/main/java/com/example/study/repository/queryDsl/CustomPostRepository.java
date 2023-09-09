package com.example.study.repository.queryDsl;

import com.example.study.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomPostRepository {

    Optional<Post> findByIdAndTitle(Long id, String title);

    List<Post> findAll(Pageable pageable);
}
