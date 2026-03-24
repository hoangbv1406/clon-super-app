package com.project.shopapp.domains.social.repository;

import com.project.shopapp.domains.social.entity.UserInteraction;
import com.project.shopapp.domains.social.enums.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {

    // Tìm hành động LIKE/UNLIKE cuối cùng của user đối với 1 post để biết họ ĐANG like hay không
    @Query(value = "SELECT action_type FROM user_interactions " +
            "WHERE user_id = :userId AND post_id = :postId " +
            "AND action_type IN ('LIKE', 'UNLIKE') " +
            "ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLatestLikeActionStatus(Integer userId, Long postId);
}