package com.restaurant.management.web.controller.scrumboard;

import com.restaurant.management.domain.scrumboard.Board;
import com.restaurant.management.domain.scrumboard.dto.*;
import com.restaurant.management.service.scrumboard.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public BoardDTO createNewEmptyBoard() {
        Board board = boardService.createEmptyBoard();


        SettingsDTO settingsDTO = new SettingsDTO(
                board.getBoardSettings().getId(),
                board.getBoardSettings().getColor(),
                board.getBoardSettings().getSubscribed(),
                board.getBoardSettings().getCardCoverImages()
        );

        String cardsIds1 = "1L, 2L, 3L";
        String cardsIds2 = "1L, 2L, 3L";
        BoardListDTO boardListDTO1 = new BoardListDTO(1L, "Name of list 1", cardsIds1.split(", "));
        BoardListDTO boardListDTO2 = new BoardListDTO(2L, "Name of list 2", cardsIds2.split(", "));

        String membersIds = "1L, 2L, 3L";
        String labelsIds = "1L, 2L, 3L";
        AttachmentDTO attachmentDTO1 = new AttachmentDTO(1L, "Name1", "src", "23/09", "type");
        AttachmentDTO attachmentDTO2 = new AttachmentDTO(2L, "Name2", "src", "23/09", "type");

        CheckItemDTO checkItemDTO1 = new CheckItemDTO(1L, "CheckItem1", true);
        CheckItemDTO checkItemDTO2 = new CheckItemDTO(2L, "CheckItem2", true);
        CheckListDTO checkListDTO = new CheckListDTO(1L, "CheckList1", Arrays.asList(checkItemDTO1, checkItemDTO2));

        ActivityDTO activityDTO1 = new ActivityDTO(1L, "Comment", "memberId2", "Message1", "23/09");
        ActivityDTO activityDTO2 = new ActivityDTO(2L, "Comment", "memberId1", "Message1", "24/09");

        MemberDTO memberDTO1 = new MemberDTO(1L, "Member1", "avatar1");
        MemberDTO memberDTO2 = new MemberDTO(2L, "Member2", "avatar2");

        LabelDTO labelDTO1 = new LabelDTO(1L, "Label1", "className1");
        LabelDTO labelDTO2 = new LabelDTO(2L, "Label2", "className2");

        CardDTO cardDTO = new CardDTO(
                1L,
                "Card 1",
                "description",
                "23/09",
                "attachId",
                membersIds.split(", "),
                labelsIds.split(", "),
                true,
                Arrays.asList(attachmentDTO1, attachmentDTO2),
                Collections.singletonList(checkListDTO),
                Arrays.asList(activityDTO1, activityDTO2)
        );

        BoardDTO boardDTO = new BoardDTO(
                board.getId(),
                board.getName(),
                board.getUri(),
                settingsDTO,
                Arrays.asList(boardListDTO1, boardListDTO2),
                Collections.singletonList(cardDTO),
                Arrays.asList(memberDTO1, memberDTO2),
                Arrays.asList(labelDTO1, labelDTO2)
        );


        return boardDTO;
    }
}
