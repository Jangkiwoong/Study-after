package com.example.study.service;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.entity.Post;
import com.example.study.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시물 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.saveAndFlush(post);
        return new PostResponseDto(post);
    }

    //게시물 조회
    public  List<PostResponseDto> readPost(PostRequestDto postRequestDto) {
        List<Post> postlist = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post : postlist) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
    }


    public PostResponseDto updatePost(Long postID, PostRequestDto postRequestDto) {
       Post post = postRepository.findById(postID).orElseThrow(
               () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
       );
       post.update(postRequestDto);
       postRepository.save(post);
       return new PostResponseDto(post);
    }
}
