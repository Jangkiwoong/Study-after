package com.example.study.dto;

import com.example.study.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
public class PostResponseDto {
    private String title;
    private String contents;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
