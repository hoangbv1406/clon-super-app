package com.project.shopapp.domains.finance.entity;

import com.project.shopapp.domains.finance.enums.WalletTransType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id", nullable = false, updatable = false)
    private Integer walletId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", insertable = false, updatable = false)
    private Wallet wallet;

    // Âm hay Dương phụ thuộc vào logic xử lý, nhưng ở đây có thể lưu số tuyệt đối + Type
    @Column(name = "amount", precision = 15, scale = 2, nullable = false, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50, nullable = false, updatable = false)
    private WalletTransType type;

    @Column(name = "description", length = 255, updatable = false)
    private String description;

    @Column(name = "ref_order_id", updatable = false)
    private Long refOrderId;

    @Column(name = "reference_code", length = 100, updatable = false)
    private String referenceCode;

    @Column(name = "balance_before", precision = 15, scale = 2, nullable = false, updatable = false)
    private BigDecimal balanceBefore;

    @Column(name = "balance_after", precision = 15, scale = 2, nullable = false, updatable = false)
    private BigDecimal balanceAfter;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}