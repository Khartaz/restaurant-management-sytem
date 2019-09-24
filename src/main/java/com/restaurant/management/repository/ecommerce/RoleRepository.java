package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.Role;
import com.restaurant.management.domain.ecommerce.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}