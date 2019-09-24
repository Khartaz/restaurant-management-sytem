package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.CheckItem;
import com.restaurant.management.domain.scrumboard.dto.CheckItemDTO;
import org.springframework.stereotype.Component;

@Component
public final class CheckItemMapper {

    public CheckItem mapToCheckItem(CheckItemDTO checkItemDTO) {
        return new CheckItem(
                checkItemDTO.getId(),
                checkItemDTO.getName(),
                checkItemDTO.getChecked()
        );
    }

    public CheckItemDTO mapToCheckItemDTO(CheckItem checkItem) {
        return new CheckItemDTO(
                checkItem.getId(),
                checkItem.getName(),
                checkItem.getChecked()
        );
    }
}
