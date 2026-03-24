package com.project.shopapp.domains.finance.entity;

import com.project.shopapp.domains.sales.entity.Order;
import com.project.shopapp.domains.sales.entity.OrderShop;
import com.project.shopapp.domains.finance.enums.GatewayTransactionStatus;
import com.project.shopapp.domains.finance.enums.GatewayTransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Column(name = "order_shop_id")
    private Integer orderShopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shop_id", insertable = false, updatable = false)
    private OrderShop orderShop;

    @Column(name = "payment_method", length = 50, nullable = false)
    private String paymentMethod;

    @Column(name = "transaction_code", length = 100)
    private String transactionCode; // Mã giao dịch của VNPAY/MOMO trả về

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Builder.Default
    private GatewayTransactionType type = GatewayTransactionType.PAYMENT;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private GatewayTransactionStatus status = GatewayTransactionStatus.PENDING;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response_json", columnDefinition = "json")
    private Map<String, Object> responseJson;

    @Column(name = "gateway_code", length = 50)
    private String gatewayCode;

    @Column(name = "error_message", length = 255)
    private String errorMessage;

    @Column(name = "parent_transaction_id")
    private Integer parentTransactionId;

    // Self-join: Trỏ tới Giao dịch gốc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_transaction_id", insertable = false, updatable = false)
    private Transaction parentTransaction;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Integer updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}