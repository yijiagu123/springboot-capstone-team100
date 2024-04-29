package com.team100.springboot.repository;

import com.team100.springboot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByPostAtDesc(String titleKeyword, String contentKeyword, Pageable pageable);

    Page<Post> findAllByUserIdAndTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByPostAtDesc(Long userId, String title, String content, Pageable pageable);

    Page<Post> findAllByUserIdOrderByPostAtDesc(Long userId, Pageable pageable);

    Page<Post> getAllByOrderByPostAtDesc(Pageable pageable);
//    Page<Post> findAllOrderByPostAtDesc(Pageable pageable);

}
