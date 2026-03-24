package com.project.shopapp.domains.marketing.entity;

import com.project.shopapp.domains.marketing.enums.BannerPlatform;
import com.project.shopapp.domains.marketing.enums.BannerPosition;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Banner extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "image_url", length = 300, nullable = false)
    private String imageUrl;

    @Column(name = "target_url", length = 300)
    private String targetUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 50)
    @Builder.Default
    private BannerPosition position = BannerPosition.HOME_MAIN;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    @Builder.Default
    private BannerPlatform platform = BannerPlatform.ALL;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(name = "click_count")
    @Builder.Default
    private Integer clickCount = 0;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // Kiểm tra xem Banner có đang trong thời gian hiệu lực không
    public boolean isValidToDisplay() {
        if (!this.isActive) return false;
        LocalDateTime now = LocalDateTime.now();
        if (this.startTime != null && now.isBefore(this.startTime)) return false;
        if (this.endTime != null && now.isAfter(this.endTime)) return false;
        return true;
    }
}