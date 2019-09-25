package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.User;
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
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndIsDeletedIsFalse(String email);

    Optional<User> findByIdAndIsDeletedIsFalse(Long id);

    Optional<User> findByRolesAndCompanyAndIsDeletedIsFalse(Set<Role> roles, Company company);

    Page<User> findAllByCompanyAndIsDeletedIsFalse(Company company, Pageable pageable);

    void deleteById(Long id);

    Boolean existsByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByEmailAndCompanyIdAndIsDeletedIsFalse(String email, Long companyId);

    List<User> findAllByIdIn(List<Long> personnelIds);

    Optional<User> findUserByEmailVerificationToken(String token);

    Optional<User> findUserByPasswordResetToken(String token);

    @Override
    Page<User> findAll(Pageable pageable);

    Optional<User> findByIdAndCompanyIdAndIsDeletedIsFalse(Long id, Long companyId);

}