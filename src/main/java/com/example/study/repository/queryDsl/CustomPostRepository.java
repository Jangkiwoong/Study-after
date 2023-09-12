package com.example.study.repository.queryDsl;

import com.example.study.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
    Page<Post> findAll(Pageable pageable);

    /**
     * queryDsl 메서드 생성시 jpa의 메서드 이름 형식에 맞춰서 생성해줘야함 아니면 service단에서 PostRepository를 생성자 의존 주입을
     * 못시키는 경우가 발생 할 수 도있음.
     */
}
