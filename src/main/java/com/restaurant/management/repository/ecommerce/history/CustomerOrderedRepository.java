package com.restaurant.management.repository.ecommerce.history;

import com.restaurant.management.domain.ecommerce.CustomerOrdered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderedRepository extends JpaRepository<CustomerOrdered, Long> {

}
