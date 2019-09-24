package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.Board;
import com.restaurant.management.domain.scrumboard.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public final class BoardMapper {
    private BoardSettingsMapper boardSettingsMapper;
    private BoardListMapper boardListMapper;
    private CardMapper cardMapper;
    private MemberMapper memberMapper;
    private LabelMapper labelMapper;

    @Autowired
    public BoardMapper(BoardSettingsMapper boardSettingsMapper,
                       BoardListMapper boardListMapper,
                       CardMapper cardMapper,
                       MemberMapper memberMapper,
                       LabelMapper labelMapper) {
        this.boardSettingsMapper = boardSettingsMapper;
        this.boardListMapper = boardListMapper;
        this.cardMapper = cardMapper;
        this.memberMapper = memberMapper;
        this.labelMapper = labelMapper;
    }

    public BoardDTO mapToBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getName(),
                board.getUri(),
                boardSettingsMapper.mapToBoardSettingsDTO(board.getBoardSettings()),
                board.getLists().stream()
                        .map(v -> boardListMapper.mapToBoardListDTO(v))
                        .collect(Collectors.toList()),
                board.getCards().stream()
                        .map(v -> cardMapper.mapToCardDTO(v))
                        .collect(Collectors.toList()),
                board.getMembers().stream()
                        .map(v -> memberMapper.mapToMemberDTO(v))
                        .collect(Collectors.toList()),
                board.getLabels().stream()
                        .map(v -> labelMapper.mapToLabelDTO(v))
                        .collect(Collectors.toList())
        );
    }
}
