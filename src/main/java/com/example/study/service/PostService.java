package com.example.study.service;

import com.example.study.dto.PostRequestDto;
import com.example.study.dto.PostResponseDto;
import com.example.study.entity.Post;
import com.example.study.global.util.Message;
import com.example.study.repository.PostFileRepository;
import com.example.study.repository.PostRepository;
import com.example.study.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.study.entity.PostFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final S3Uploader s3Uploader;

    //게시물 작성
    @Transactional
    public ResponseEntity<Message> createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.saveAndFlush(post);
        return new ResponseEntity<>(new Message("게시글이 작성 되었습니다.",new PostResponseDto(post)), HttpStatus.OK);
    }

    //게시물 조회 (페이지네이션)
    @Transactional
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
    @Transactional
    public ResponseEntity<Message> readParamPost(Long id, String title) {
        Post post = postRepository.findByIdAndTitle(id, title).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return new ResponseEntity<>(new Message(null, post), HttpStatus.OK);
    }

    //게시물 수정
    @Transactional
    public ResponseEntity<Message> updatePost(Long postID, PostRequestDto postRequestDto) {
       Post post = postRepository.findById(postID).orElseThrow(
               () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
       );
       post.update(postRequestDto);
       postRepository.save(post);
        return new ResponseEntity<>(new Message("게시글이 수정 되었습니다.",new PostResponseDto(post)), HttpStatus.OK);
    }

    //게시물 삭제
    @Transactional
    public ResponseEntity<Message> deletePost(Long postID) {
        postRepository.findById(postID).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        postRepository.deleteById(postID);
        return new ResponseEntity<>(new Message("게시글이 삭제 되었습니다.",null), HttpStatus.OK);
    }

    //이미지 추가
    @Transactional
    public ResponseEntity<Message> createImagePost(PostRequestDto postRequestDto) throws IOException {
        String imgUrl = s3Uploader.upload(postRequestDto.getImage());
        Post post = new Post(imgUrl, postRequestDto);
        postRepository.saveAndFlush(post);

        return new ResponseEntity<>(new Message("이미지 등록 성공", null), HttpStatus.CREATED);
    }

    //파일 업로드
    @Transactional
    public ResponseEntity<Message> createfile(MultipartFile multipartFile) throws IOException {
        // 현재 프로젝트 디렉토리의 경로를 가져옴 (현재 생성되는 디렉토리의 경로 + 파일을 저장할 경로)
        // System.getProperty("user.dir")은 자바에서 현재 실행중인 디렉토리 위치를 알 수 있다.
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 파일 이름에 UUID를 추가하여 중복을 피함
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();

        // 파일의 상대 경로를 생성 (클라이언트가 파일을 다운로드 할때 필요한 경로)
        String filePath =  "/files/" + fileName;

        // 파일을 저장할 위치와 이름을 설정
        File saveFile = new File(projectPath, fileName);

        // 업로드된 파일의 내용을 실제 파일로 복사하여 저장
        multipartFile.transferTo(saveFile);

        // 업로드된 파일 정보를 데이터베이스에 저장하기 위한 PostFile 객체 생성
        PostFile postFile = new PostFile();
        postFile.setFileName(fileName);
        postFile.setFilePath(filePath);

        // PostFile 객체를 데이터베이스에 저장
        postFileRepository.save(postFile);
        return new ResponseEntity<>(new Message("파일 저장 성공", multipartFile.getOriginalFilename()), HttpStatus.OK);
    }
}
