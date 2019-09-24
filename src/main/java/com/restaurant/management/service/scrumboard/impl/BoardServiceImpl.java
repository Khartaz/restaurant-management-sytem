package com.restaurant.management.service.scrumboard.impl;

import com.restaurant.management.domain.scrumboard.Board;
import com.restaurant.management.domain.scrumboard.BoardSettings;
import com.restaurant.management.domain.scrumboard.Label;
import com.restaurant.management.service.scrumboard.BoardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class BoardServiceImpl implements BoardService {

    public Board createEmptyBoard() {
        Board board = new Board();

        Label sampleLabel1 = new Label("High Priority", "bd-red text-white");
        Label sampleLabel2 = new Label("Design", "bg-orange text-white");
        Label sampleLabel3 = new Label("App", "bd-blue text-white");
        Label sampleLabel4 = new Label("Feature", "bd-green text-white");

        board.setName("Untitled Board");
        board.setUri("untitled-board");
        board.setBoardSettings(new BoardSettings("", true, false));
        board.setLists(new ArrayList<>());
        board.setCards(new ArrayList<>());
        board.setMembers(new ArrayList<>());
        board.setLabels(Arrays.asList(sampleLabel1, sampleLabel2, sampleLabel3, sampleLabel4));

        return board;
    }
}
