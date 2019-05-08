package com.restaurant.management.repository;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.RestaurantInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {

    Optional<AccountUser> findByUsernameOrEmail(String username, String email);

    Optional<AccountUser> findById(Long id);

    void deleteById(Long id);

    Boolean existsByEmail(String email);

    Optional<AccountUser> findAdminUserByEmailVerificationToken(String token);

    Optional<AccountUser> findAdminUserByPasswordResetToken(String token);

    @Override
    Page<AccountUser> findAll(Pageable pageable);

    List<AccountUser> findByRestaurantInfoId(Long id);

}