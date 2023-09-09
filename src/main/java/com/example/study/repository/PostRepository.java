package com.example.study.repository;

import com.example.study.entity.Post;
//import com.example.study.repository.queryDsl.CustomPostRepository;
import com.example.study.repository.queryDsl.CustomPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {


}
