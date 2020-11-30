package io.involvedapps.taquingame.model;

import java.util.List;

public class Board {

    private List<Integer> _board;
    private int _size;

    public Board(List<Integer> board, int size) {
        _board = board;
        _size = size;
    }

    public List<Integer> getBoard() {
        return _board;
    }

    public int getSize() {
        return _size;
    }

    public int getIndex(int cellValue) {
        return _board.indexOf(cellValue);
    }

    public int getIndexEmptyCell() {
        return _board.indexOf(-1);
    }

    public void switchEmptyCell(int index) {
        _board.set(getIndexEmptyCell(), _board.get(index));
        _board.set(index, -1);
    }
}
