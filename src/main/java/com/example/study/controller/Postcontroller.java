package com.example.study.controller;

import com.example.study.dto.PostRequestDto;
import com.example.study.global.util.Message;
import com.example.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Postcontroller {

    private final PostService postService;

    //게시물 작성
    @PostMapping("/post")
    public ResponseEntity<Message> createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    //게시물 조회
    @GetMapping("/posts")
    public ResponseEntity<Message> readPost(@RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postService.readPost(pageable);
        /**
          http://localhost:8080/api/posts?pageNumber=0&pageSize=4
         **/
    }

    //게시물 상세조회
    @GetMapping("/post-param")
    public ResponseEntity<Message> readParamPost(@RequestParam Long id, @RequestParam String title) {
        return postService.readParamPost(id, title);
        /**
         http://localhost:8080/api/post-param?id=1&title=장기웅
         **/
    }

    //게시물 수정
   @PatchMapping("/post/{postId}")
    public ResponseEntity<Message> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
   }
   //게시물 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Message> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    // 이미지 추가
    @PostMapping(value = "/admin/bean", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createImagePost (@ModelAttribute PostRequestDto postRequestDto) throws IOException {
        return postService.createImagePost(postRequestDto);
    }
//    //테스트 코드
//    @GetMapping("/hello")
//    public String helloworld(){
//        return "CICD test";
//    }
}
