package com.restaurant.management.repository;

import com.restaurant.management.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AccountUser, Long> {
    Optional<AdminUser> findByEmail(String email);

    Optional<AdminUser> findByUsernameOrEmail(String username, String email);

    List<AdminUser> findByIdIn(List<Long> userIds);

    Optional<AdminUser> findByUsername(String username);

    Optional<AdminUser> findAdminUserByUserUniqueId(String userUniqueId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<AdminUser> findAdminUserByEmailVerificationToken(String token);

    Optional<AdminUser> findAdminUserByPasswordResetToken(String token);
}