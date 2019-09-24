package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.BoardList;
import com.restaurant.management.domain.scrumboard.dto.BoardListDTO;
import org.springframework.stereotype.Component;

@Component
public final class BoardListMapper {

    public BoardListDTO mapToBoardListDTO(BoardList boardList) {
        return new BoardListDTO(
                boardList.getId(),
                boardList.getName(),
                boardList.getCardsIds().split(", ")
        );
    }
}
