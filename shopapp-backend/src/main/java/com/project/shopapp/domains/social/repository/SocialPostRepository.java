package com.project.shopapp.domains.social.repository;

import com.project.shopapp.domains.social.entity.SocialPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialPostRepository extends JpaRepository<SocialPost, Long>, JpaSpecificationExecutor<SocialPost> {

    // Load tường nhà của 1 người
    Page<SocialPost> findByUserIdAndStatusAndIsDeleted(Integer userId, String status, Long isDeleted, Pageable pageable);

    // Tăng Like
    @Modifying
    @Query("UPDATE SocialPost p SET p.totalLikes = p.totalLikes + 1 WHERE p.id = :postId AND p.isDeleted = 0")
    void incrementLikes(Long postId);

    // Giảm Like (Unlike)
    @Modifying
    @Query("UPDATE SocialPost p SET p.totalLikes = p.totalLikes - 1 WHERE p.id = :postId AND p.totalLikes > 0 AND p.isDeleted = 0")
    void decrementLikes(Long postId);

    // Tăng Comment
    @Modifying
    @Query("UPDATE SocialPost p SET p.totalComments = p.totalComments + 1 WHERE p.id = :postId AND p.isDeleted = 0")
    void incrementComments(Long postId);
}