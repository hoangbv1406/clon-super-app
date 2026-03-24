package com.project.shopapp.domains.pos.repository;

import com.project.shopapp.domains.pos.entity.PosSession;
import com.project.shopapp.domains.pos.enums.PosSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PosSessionRepository extends JpaRepository<PosSession, Integer>, JpaSpecificationExecutor<PosSession> {

    // Một thu ngân chỉ được có 1 ca đang OPEN tại 1 cửa hàng
    boolean existsByShopIdAndUserIdAndStatus(Integer shopId, Integer userId, PosSessionStatus status);

    // Tìm ca đang mở của một nhân viên
    Optional<PosSession> findByShopIdAndUserIdAndStatus(Integer shopId, Integer userId, PosSessionStatus status);
}