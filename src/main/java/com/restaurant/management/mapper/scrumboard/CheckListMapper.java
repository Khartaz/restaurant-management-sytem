package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.CheckList;
import com.restaurant.management.domain.scrumboard.dto.CheckListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public final class CheckListMapper {
    private CheckItemMapper checkItemMapper;


    @Autowired
    public CheckListMapper(CheckItemMapper checkItemMapper) {
        this.checkItemMapper = checkItemMapper;
    }

    public CheckList mapToCheckList(CheckListDTO checkListDTO) {
        return new CheckList(
                checkListDTO.getId(),
                checkListDTO.getName(),
                checkListDTO.getCheckItems().stream()
                        .map(v -> checkItemMapper.mapToCheckItem(v))
                        .collect(Collectors.toList())
        );
    }

    public CheckListDTO mapToCheckListDTO(CheckList checkList) {
        return new CheckListDTO(
                checkList.getId(),
                checkList.getName(),
                checkList.getCheckItems().stream()
                        .map(v -> checkItemMapper.mapToCheckItemDTO(v))
                        .collect(Collectors.toList())
        );
    }
}
