package com.example.study.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String contents;
    private MultipartFile Image;
}

/* @Data사용한다면 @Getter사용시 데이터가 null인 경우에도 호출이 가능하고
    @Satter을 사용해서 MultipartFile로 받은 file을 PostRequestDto의 image에 값을 설정해주고 PostService로 넘겨줘야함

    혹은 @Getter와 @Setter을 같이 써줘도 가능
 */

/**
 * MultipartFile은 multipart/form-data 요청에서 업로드된 파일을 다루는 데 유용
 */
