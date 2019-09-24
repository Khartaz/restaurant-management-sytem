package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {

    Optional<AccountUser> findByEmailAndIsDeletedIsFalse(String email);

    Optional<AccountUser> findByIdAndIsDeletedIsFalse(Long id);

    Optional<AccountUser> findByRolesAndCompanyAndIsDeletedIsFalse(Set<Role> roles, Company company);

    Page<AccountUser> findAllByCompanyAndIsDeletedIsFalse(Company company, Pageable pageable);

    void deleteById(Long id);

    Boolean existsByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByEmailAndCompanyIdAndIsDeletedIsFalse(String email, Long companyId);

    List<AccountUser> findAllByIdIn(List<Long> personnelIds);

    Optional<AccountUser> findUserByEmailVerificationToken(String token);

    Optional<AccountUser> findUserByPasswordResetToken(String token);

    @Override
    Page<AccountUser> findAll(Pageable pageable);

    Optional<AccountUser> findByIdAndCompanyIdAndIsDeletedIsFalse(Long id, Long companyId);

}