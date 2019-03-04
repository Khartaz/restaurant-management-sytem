package com.restaurant.management.repository;

import com.restaurant.management.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByEmail(String email);

    Optional<AccountUser> findByUsernameOrEmail(String username, String email);

    Optional<AccountUser> findByUserUniqueId(String userUniqueId);

    void deleteByUserUniqueId(String userUniqueId);

    void deleteById(Long id);

    List<AccountUser> findByIdIn(List<Long> userIds);

    Optional<AccountUser> findByUsername(String username);

    Optional<AccountUser> findAdminUserByUserUniqueId(String userUniqueId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<AccountUser> findAdminUserByEmailVerificationToken(String token);

    Optional<AccountUser> findAdminUserByPasswordResetToken(String token);
}