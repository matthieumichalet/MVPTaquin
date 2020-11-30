package io.involvedapps.taquingame.presenter;

import io.involvedapps.taquingame.model.Board;
import io.involvedapps.taquingame.model.Game;

public class GamePresenter implements Game.Presenter {

    private Game _game;
    private View _linkedview;

    public GamePresenter(View linkedView) {
         _game = new Game(this);
        _linkedview = linkedView;
    }

    public void InitGame(int boardSize) {
        _game.InitGame(boardSize);
    }

    public void clickOnCell(int cellValue) {
        _game.clickOnCell(cellValue);
    }

    @Override
    public void startGame(Board board) {
        _linkedview.startGame(board);
    }

    @Override
    public void updateBoard(Board board) {
        _linkedview.updateBoardUI(board);
    }

    @Override
    public void wonGame() {
        _linkedview.wonGame();
    }

    public void askToStart() {
        _linkedview.askBoardsize(_game.getMinBoardSize(), _game.getMaxBoardSize(), _game.getDefaultBoardSize());
    }

    public interface View {
        void askBoardsize(int minBoardSize, int maxBoardSize, int defaultValue);
        void startGame(Board board);
        void updateBoardUI(Board board);
        void wonGame();
    }
}
