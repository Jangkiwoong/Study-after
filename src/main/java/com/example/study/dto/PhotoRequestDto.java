package com.example.study.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoRequestDto {
    private MultipartFile Image;
}
