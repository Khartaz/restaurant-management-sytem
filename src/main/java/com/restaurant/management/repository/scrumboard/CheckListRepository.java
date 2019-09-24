package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
}
