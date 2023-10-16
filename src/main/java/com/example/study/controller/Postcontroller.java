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
import org.springframework.web.multipart.MultipartFile;

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
                                            @RequestParam(defaultValue = "10") int pageSize)
    {
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

    // 이미지 추가 S3
    @PostMapping(value = "/post/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createImagePost (@ModelAttribute  PostRequestDto postRequestDto) throws IOException {
        return postService.createImagePost(postRequestDto);
    }

    /**
     *
     * @ModelAttribute는 클라이언트에서 요청한 multipart/form-data 형태의 HTTP BODY를 setter를 통해 오브젝트 형태로 맵핑해주는 어노테이션
     * setter가 없다면 매핑에 실패하여 null을 갖게 된다.
     */

    //파일 저장
    @PostMapping(value = "/post/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createfile(@ModelAttribute MultipartFile multipartFile) throws IOException {
        return postService.createfile(multipartFile);
    }
    //https://www.youtube.com/watch?v=qeB2GzrSFAc&list=PLZzruF3-_clsWF2aULPsUPomgolJ-idGJ&index=14

    //웹소캣


//    //테스트 코드
//    @GetMapping("/hello")
//    public String helloworld(){
//        return "CICD test";
//    }
}
