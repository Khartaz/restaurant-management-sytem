package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.AccountUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {

    Optional<AccountUser> findByEmail(String email);

    Optional<AccountUser> findById(Long id);

    void deleteById(Long id);

    Boolean existsByEmail(String email);

    Optional<AccountUser> findUserByEmailVerificationToken(String token);

    Optional<AccountUser> findUserByPasswordResetToken(String token);

    @Override
    Page<AccountUser> findAll(Pageable pageable);

    Optional<AccountUser> findByIdAndCompanyId(Long id, Long companyId);

    Page<AccountUser> findAllByCompanyId(Long companyId, Pageable pageable);

}