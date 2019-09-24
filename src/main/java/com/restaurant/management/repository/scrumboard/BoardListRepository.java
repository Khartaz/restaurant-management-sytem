package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
}
