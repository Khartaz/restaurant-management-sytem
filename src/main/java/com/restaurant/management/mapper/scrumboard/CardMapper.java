package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.Card;
import com.restaurant.management.domain.scrumboard.dto.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public final class CardMapper {
    private AttachmentMapper attachmentMapper;
    private CheckListMapper checkListMapper;
    private ActivityMapper activityMapper;

    @Autowired
    public CardMapper(AttachmentMapper attachmentMapper,
                      CheckListMapper checkListMapper,
                      ActivityMapper activityMapper) {
        this.attachmentMapper = attachmentMapper;
        this.checkListMapper = checkListMapper;
        this.activityMapper = activityMapper;
    }

    public CardDTO mapToCardDTO(Card card) {
        return new CardDTO(
                card.getId(),
                card.getName(),
                card.getDescription(),
                card.getDueDate(),
                card.getIdAttachmentCover(),
                card.getMembersIds().split(", "),
                card.getLabelsIds().split(", "),
                card.getSubscribed(),
                card.getAttachments().stream()
                        .map(v -> attachmentMapper.mapToAttachmentDTO(v))
                        .collect(Collectors.toList()),
                card.getCheckLists().stream()
                        .map(v -> checkListMapper.mapToCheckListDTO(v))
                        .collect(Collectors.toList()),
                card.getActivities().stream()
                        .map(v -> activityMapper.mapToActivityDTO(v))
                        .collect(Collectors.toList())
        );
    }
}
