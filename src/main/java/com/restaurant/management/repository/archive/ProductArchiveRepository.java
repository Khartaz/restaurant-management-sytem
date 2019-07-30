package com.restaurant.management.repository.archive;

import com.restaurant.management.domain.ecommerce.archive.ProductArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductArchiveRepository extends JpaRepository<ProductArchive, Long> {
}
