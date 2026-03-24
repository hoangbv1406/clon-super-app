package com.project.shopapp.domains.sales.entity;

import com.project.shopapp.domains.identity.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Column(name = "order_shop_id", updatable = false)
    private Integer orderShopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shop_id", insertable = false, updatable = false)
    private OrderShop orderShop;

    @Column(name = "status", length = 50, nullable = false, updatable = false)
    private String status;

    @Column(name = "note", columnDefinition = "TEXT", updatable = false)
    private String note;

    @Column(name = "updated_by", updatable = false)
    private Integer updatedBy;

    // Join để hiện tên người thao tác (Admin XYZ đã xác nhận đơn)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private User updater;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}