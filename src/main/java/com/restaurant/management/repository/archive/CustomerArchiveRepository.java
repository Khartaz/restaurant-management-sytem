package com.restaurant.management.repository.archive;

import com.restaurant.management.domain.archive.CustomerArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerArchiveRepository extends JpaRepository<CustomerArchive, Long> {

    boolean existsByPhoneNumber(Long phoneNumber);

    Optional<CustomerArchive> findByPhoneNumber(Long phoneNumber);
}
