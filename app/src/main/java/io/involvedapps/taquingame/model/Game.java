package io.involvedapps.taquingame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private Presenter _presenter;
    private Board _board;
    private int _minBoardSize = 2;
    private int _maxBoardSize = 6;
    private int _defaultBoardSize = 4;
    private int _currentBoardSize = 2;

    public Game(Presenter presenter) {
        _presenter = presenter;
    }

    public void InitGame(int boardSize) {
        List<Integer> boardCells = CreateBoardCells(boardSize);
        _board = new Board(boardCells, boardSize);
        _presenter.startGame(_board);
    }

    private List<Integer> CreateBoardCells(int boardSize) {
        _currentBoardSize = boardSize;

        ArrayList<Integer> cells = new ArrayList<>();
        int maxIndex = (boardSize * boardSize) - 1;

        //add all normals cell
        for (int i = 1; i <= maxIndex; ++i) {
            cells.add(i);
        }

        //add the missing cell
        cells.add(-1);

        //shuffles cells
        Collections.shuffle(cells);

        return cells;
    }

    public int getMinBoardSize() {
        return _minBoardSize;
    }

    public int getMaxBoardSize() {
        return _maxBoardSize;
    }

    public void clickOnCell(int cellValue) {
        int index = _board.getIndex(cellValue);
        int newPlaceOnBoard = findNewPlaceOnBoard(index);

        if (newPlaceOnBoard != -1) {
            _board.switchEmptyCell(index);
            _presenter.updateBoard(_board);
            checkWinCondition();
        }
    }

    private void checkWinCondition() {
        List<Integer> boardCells = _board.getBoard();
        int valToCheck = 1;

        for (int i = 0 ; i < boardCells.size(); ++i) {
            if (valToCheck == boardCells.get(i) /*normal cell*/ ||
                    i + 1 == boardCells.size() /*last cell need to be the -1*/) {
                valToCheck++;
            } else {
                return;
            }
        }
        _presenter.wonGame();
    }

    private int findNewPlaceOnBoard(int index) {
        int emptyCellIndex = _board.getIndexEmptyCell();
        int moduloEmptyCell = emptyCellIndex % _currentBoardSize;

        if (index + 1 == emptyCellIndex && moduloEmptyCell != 0 /*right*/ ||
            index - 1 == emptyCellIndex && moduloEmptyCell != (_currentBoardSize-1) /*left*/ ||
            index - _currentBoardSize == emptyCellIndex /*top*/ ||
            index + _currentBoardSize == emptyCellIndex /*bottom*/) {
            return emptyCellIndex;
        }
        return -1;
    }

    public int getDefaultBoardSize() {
        return _defaultBoardSize;
    }

    public interface Presenter {
        void startGame(Board board);
        void updateBoard(Board board);
        void wonGame();
    }
}
