package com.example.study.service;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.entity.Post;
import com.example.study.global.util.Message;
import com.example.study.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Message> createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.saveAndFlush(post);
        return new ResponseEntity<>(new Message("게시글이 작성 되었습니다.",new PostResponseDto(post)), HttpStatus.OK);
    }

    //게시물 조회
    public  ResponseEntity<Message> readPost(Pageable pageable) {
        Page<Post> postlist = (Page<Post>) postRepository.findAll(pageable);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post2 : postlist) {
            postResponseDtoList.add(new PostResponseDto(post2));
        }
        return new ResponseEntity<>(new Message("게시글 전체 조회.",postlist), HttpStatus.OK);
    }

    //게시물 상세조회
    public ResponseEntity<Message> readParamPost(Long id, String title) {
        Post post = postRepository.findByIdAndTitle(id, title).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return new ResponseEntity<>(new Message(null, post), HttpStatus.OK);
    }

    //게시물 수정
    public ResponseEntity<Message> updatePost(Long postID, PostRequestDto postRequestDto) {
       Post post = postRepository.findById(postID).orElseThrow(
               () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
       );
       post.update(postRequestDto);
       postRepository.save(post);
        return new ResponseEntity<>(new Message("게시글이 수정 되었습니다.",new PostResponseDto(post)), HttpStatus.OK);
    }

    //게시물 삭제
    public ResponseEntity<Message> deletePost(Long postID) {
        postRepository.findById(postID).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        postRepository.deleteById(postID);
        return new ResponseEntity<>(new Message("게시글이 삭제 되었습니다.",null), HttpStatus.OK);
    }
}
