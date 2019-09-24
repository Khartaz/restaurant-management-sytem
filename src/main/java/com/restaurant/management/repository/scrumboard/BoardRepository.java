package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
