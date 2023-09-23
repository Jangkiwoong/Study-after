package com.example.study.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {
    private String title;
    private String contents;
    private MultipartFile image;
}
