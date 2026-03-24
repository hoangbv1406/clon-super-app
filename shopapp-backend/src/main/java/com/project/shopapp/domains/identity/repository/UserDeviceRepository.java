package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer>, JpaSpecificationExecutor<UserDevice> {

    // Tìm device theo UID của user
    Optional<UserDevice> findByUserIdAndDeviceUid(Integer userId, String deviceUid);

    // Lấy tất cả token đang active của 1 user để gửi thông báo (VD: "Đơn hàng đã được giao")
    @Query("SELECT d.fcmToken FROM UserDevice d WHERE d.userId = :userId AND d.isActive = true")
    List<String> findActiveFcmTokensByUserId(Integer userId);

    // Vô hiệu hóa token bị lỗi (Gọi khi Firebase trả về error Unregistered)
    @Modifying
    @Query("UPDATE UserDevice d SET d.isActive = false WHERE d.fcmToken = :token")
    void deactivateByToken(String token);
}