package com.example.study.service;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.dto.PotoRequestDto;
import com.example.study.entity.Post;
import com.example.study.global.util.Message;
import com.example.study.repository.PostRepository;
import com.example.study.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;

    //게시물 작성
    @Transactional
    public ResponseEntity<Message> createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.saveAndFlush(post);
        return new ResponseEntity<>(new Message("게시글이 작성 되었습니다.",new PostResponseDto(post)), HttpStatus.OK);
    }

    //게시물 조회 (페이지네이션)
    public  ResponseEntity<Message> readPost(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        if (postPage.isEmpty()) {
            throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
        }
        List<Post> postList = postPage.getContent();
        return new ResponseEntity<>(new Message("게시글 전체 조회.", postList), HttpStatus.OK);
        /**
         강제 형변환 시켜주면 Page객체 안에 리스트 형태인 Content를 제외한 나머지 데이터들이 유실되서 로직 수행시 에러가 나타남
         **/
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

    //이미지 추가
    public ResponseEntity<Message> createImagePost(PotoRequestDto potoRequestDto) throws IOException {
        String imgUrl = s3Uploader.upload(potoRequestDto.getImage());
        Post post = new Post(imgUrl);
        postRepository.saveAndFlush(post);

        return new ResponseEntity<>(new Message("이미지 등록 성공", null), HttpStatus.CREATED);
    }
}
