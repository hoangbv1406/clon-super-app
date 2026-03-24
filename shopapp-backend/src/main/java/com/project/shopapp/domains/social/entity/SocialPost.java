package com.project.shopapp.domains.social.entity;

import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.social.enums.PostMediaType;
import com.project.shopapp.domains.social.enums.PostStatus;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "social_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SocialPost extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    @Builder.Default
    private PostMediaType mediaType = PostMediaType.IMAGE;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media_urls", columnDefinition = "json")
    private List<String> mediaUrls;

    @Column(name = "linked_product_id")
    private Integer linkedProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_product_id", insertable = false, updatable = false)
    private Product linkedProduct;

    @Column(name = "total_likes")
    @Builder.Default
    private Integer totalLikes = 0;

    @Column(name = "total_comments")
    @Builder.Default
    private Integer totalComments = 0;

    @Column(name = "total_shares")
    @Builder.Default
    private Integer totalShares = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private PostStatus status = PostStatus.APPROVED;
}