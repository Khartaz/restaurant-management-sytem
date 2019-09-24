package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
