package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.SocialAccount;
import com.project.shopapp.domains.identity.enums.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>, JpaSpecificationExecutor<SocialAccount> {

    // Tìm account bằng ID của bên thứ 3 (Google ID)
    Optional<SocialAccount> findByProviderAndProviderId(SocialProvider provider, String providerId);

    // Lấy danh sách các MXH đã liên kết của 1 user
    List<SocialAccount> findByUserId(Integer userId);

    boolean existsByUserIdAndProvider(Integer userId, SocialProvider provider);
}