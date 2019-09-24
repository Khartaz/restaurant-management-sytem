package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
