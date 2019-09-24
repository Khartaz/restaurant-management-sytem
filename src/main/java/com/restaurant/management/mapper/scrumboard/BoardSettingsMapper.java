package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.BoardSettings;
import com.restaurant.management.domain.scrumboard.dto.BoardSettingsDTO;
import org.springframework.stereotype.Component;

@Component
public final class BoardSettingsMapper {

    public BoardSettings mapToBoardSettings(BoardSettingsDTO boardSettingsDTO) {
        return new BoardSettings(
                boardSettingsDTO.getId(),
                boardSettingsDTO.getColor(),
                boardSettingsDTO.getSubscribed(),
                boardSettingsDTO.getCardCoverImages()
        );
    }

    public BoardSettingsDTO mapToBoardSettingsDTO(BoardSettings boardSettings) {
        return new BoardSettingsDTO(
                boardSettings.getId(),
                boardSettings.getColor(),
                boardSettings.getSubscribed(),
                boardSettings.getCardCoverImages()
        );
    }
}
