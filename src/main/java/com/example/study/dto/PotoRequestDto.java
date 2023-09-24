package com.example.study.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PotoRequestDto {
    private MultipartFile image;
}
