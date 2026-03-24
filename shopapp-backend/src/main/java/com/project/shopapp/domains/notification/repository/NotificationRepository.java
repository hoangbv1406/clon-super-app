package com.project.shopapp.domains.notification.repository;

import com.project.shopapp.domains.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {

    // Đếm số thông báo chưa đọc để hiển thị Badge đỏ
    long countByUserIdAndIsReadFalseAndIsDeleted(Integer userId, Long isDeleted);

    // Tìm chi tiết 1 thông báo của User
    Optional<Notification> findByIdAndUserIdAndIsDeleted(Long id, Integer userId, Long isDeleted);

    // Đánh dấu tất cả là đã đọc (Mark all as read)
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.userId = :userId AND n.isRead = false AND n.isDeleted = 0")
    int markAllAsRead(Integer userId);
}