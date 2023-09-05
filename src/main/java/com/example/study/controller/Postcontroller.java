package com.example.study.controller;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.global.util.Message;
import com.example.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/post")
    public ResponseEntity<Message> readPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.readPost(postRequestDto);
    }
    //게시물 수정
   @PatchMapping("/post/{postId}")
    public ResponseEntity<Message> updatePost(@PathVariable Long postID, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postID, postRequestDto);
   }
   //게시물 삭제
    @DeleteMapping("/post/{postID}")
    public ResponseEntity<Message> deletePost(@PathVariable Long postID) {
        return postService.deletePost(postID);
    }
}
