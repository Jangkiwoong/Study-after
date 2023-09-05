package com.example.study.controller;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Postcontroller {

    private final PostService postService;

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/post")
    public List<PostResponseDto> readPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.readPost(postRequestDto);
    }

   @PatchMapping("/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postID, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postID, postRequestDto);
   }
}
