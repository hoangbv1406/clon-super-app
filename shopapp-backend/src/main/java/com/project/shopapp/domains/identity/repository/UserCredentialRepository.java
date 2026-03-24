package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer>, JpaSpecificationExecutor<UserCredential> {
    List<UserCredential> findByUserIdAndIsActiveTrue(Integer userId);

    // Dùng khi User verify đăng nhập bằng vân tay
    Optional<UserCredential> findByCredentialIdAndIsActiveTrue(String credentialId);

    boolean existsByCredentialId(String credentialId);
}