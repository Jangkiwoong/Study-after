package com.example.study.repository.queryDsl;

import com.example.study.entity.Post;
import com.example.study.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomPostRepositoryImple implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private static final int PAGE_SIZE = 4;



    @Override
    public Optional<Post> findByIdAndTitle(Long id, String title) {
        Post post = jpaQueryFactory.selectFrom(QPost.post)
                .where(QPost.post.Id.eq(id).and(QPost.post.title.eq(title)))
                .fetchOne();
        return Optional.ofNullable(post);
    }

    //페이징 네이션
    @Override
    public List<Post> findAll(Pageable pageable) {
 return jpaQueryFactory.selectFrom(QPost.post)
                .offset((long) ( - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .fetch();
    }
}
/**

 .fetch()은 쿼리를 실행하고 그 결과를 리스트 형태로 반환.

     .fetchOne()은 쿼리를 실행하고 결과 중 첫 번째 레코드를 반환, 결과가 없는 경우 null을 반환.

     .fetchFirst()은 쿼리를 실행하고 결과 중 첫 번째 레코드를 반환, 결과가 없는 경우 예외를 발생.

     .fetchAll()은 쿼리를 실행하고 *모든 결과*를 리스트로 반환.

     .fetchJoin()은 연관 관계를 EAGER로 로딩하도록 지정할 때 사용,
     이 메서드를 사용하면 연관된 엔티티나 컬렉션을 한 번의 쿼리로 로딩할 수 있으며,
     N+1 쿼리 문제를 방지할 수 있습니다.

     **/
