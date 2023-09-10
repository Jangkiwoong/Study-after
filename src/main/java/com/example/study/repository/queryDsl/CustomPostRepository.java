package com.example.study.repository.queryDsl;

import com.example.study.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomPostRepository {

    Page<Post> findAll(Pageable pageable);
}
