package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.Label;
import com.restaurant.management.domain.scrumboard.dto.LabelDTO;
import org.springframework.stereotype.Component;

@Component
public final class LabelMapper {

    public Label mapToLabel(LabelDTO labelDTO) {
        return new Label(
                labelDTO.getId(),
                labelDTO.getName(),
                labelDTO.getClassName()
        );
    }

    public LabelDTO mapToLabelDTO(Label label) {
        return new LabelDTO(
                label.getId(),
                label.getName(),
                label.getClassName()
        );
    }
}
