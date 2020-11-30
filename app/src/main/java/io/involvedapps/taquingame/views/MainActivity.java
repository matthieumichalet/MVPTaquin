package io.involvedapps.taquingame.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.involvedapps.taquingame.R;
import io.involvedapps.taquingame.model.Board;
import io.involvedapps.taquingame.presenter.GamePresenter;

public class MainActivity extends AppCompatActivity implements GamePresenter.View {

    private GamePresenter _presenter;

    private RecyclerView _playboard;
    private Button _restartButton;
    private Button _changeSizeButton;
    private BoardAdapter _adapter;

    private View _questionBoardsizeView;
    private View _questionPlusButton;
    private View _questionMinusButton;
    private TextView _questionValueText;

    //default values that are overwritten by the presenter
    private int _minBoardSize = 2;
    private int _maxBoardSize = 6;
    private int _currentBoardSize = 4;

    private View _winView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initQuestionView();
        initPlayboard();
        initWinView();

        _presenter = new GamePresenter(this);

        _presenter.askToStart();
    }

    @Override
    public void askBoardsize(int minBoardSize, int maxBoardSize, int defaultValue) {
        disableAll();

        _minBoardSize = minBoardSize;
        _maxBoardSize = maxBoardSize;
        _currentBoardSize = defaultValue;
        _questionBoardsizeView.setVisibility(View.VISIBLE);

        updateQuestionUI();
    }

    @Override
    public void startGame(Board board) {
        disableAll();

        _playboard.setVisibility(View.VISIBLE);
        _restartButton.setVisibility(View.VISIBLE);
        _changeSizeButton.setVisibility(View.VISIBLE);

        _playboard.setLayoutManager(new GridLayoutManager(getApplicationContext(), board.getSize()));

        _adapter = new BoardAdapter(board, this::onClickCell);
        _playboard.setAdapter(_adapter);
    }

    @Override
    public void updateBoardUI(Board board) {
        _adapter.setBoard(board);
    }

    @Override
    public void wonGame() {
        _winView.setVisibility(View.VISIBLE);
        _restartButton.setVisibility(View.GONE);
        _changeSizeButton.setVisibility(View.GONE);
    }

    private void onClickCell(int cellValue) {
        _presenter.clickOnCell(cellValue);
    }

    private void onClickGo() {
        _presenter.InitGame(_currentBoardSize);
    }

    private void onClickQuestionPlus() {
        if (_currentBoardSize < _maxBoardSize) {
            _currentBoardSize++;
        }
        updateQuestionUI();
    }

    private void onClickQuestionMinus() {
        if (_currentBoardSize > _minBoardSize) {
            _currentBoardSize--;
        }
        updateQuestionUI();
    }

    private void initQuestionView() {
        _questionBoardsizeView = findViewById(R.id.question_boardsize_view);
        _questionPlusButton = findViewById(R.id.question_button_plus);
        _questionMinusButton = findViewById(R.id.question_button_minus);
        _questionValueText = findViewById(R.id.question_value);

        _questionPlusButton.setOnClickListener(v -> onClickQuestionPlus());
        _questionMinusButton.setOnClickListener(v -> onClickQuestionMinus());

        View goButton = findViewById(R.id.question_button_go);
        goButton.setOnClickListener(v -> onClickGo());
    }

    private void initPlayboard() {
        _playboard = findViewById(R.id.playboard);
        _restartButton = findViewById(R.id.playboard_restart);
        _changeSizeButton = findViewById(R.id.playboard_change_size);

        _restartButton.setOnClickListener(v -> _presenter.InitGame(_currentBoardSize));
        _changeSizeButton.setOnClickListener(v -> _presenter.askToStart());
    }

    private void initWinView() {
        _winView = findViewById(R.id.win_view);

        Button buttonStartAnother = findViewById(R.id.win_start_again_button);
        buttonStartAnother.setOnClickListener(v -> _presenter.askToStart());
    }

    private void updateQuestionUI() {
        _questionValueText.setText(String.valueOf(_currentBoardSize));

        if (_currentBoardSize == _minBoardSize) {
            _questionMinusButton.setVisibility(View.GONE);
        } else {
            _questionMinusButton.setVisibility(View.VISIBLE);
        }

        if (_currentBoardSize == _maxBoardSize) {
            _questionPlusButton.setVisibility(View.GONE);
        } else {
            _questionPlusButton.setVisibility(View.VISIBLE);
        }
    }

    private void disableAll() {
        _questionBoardsizeView.setVisibility(View.GONE);

        _playboard.setVisibility(View.GONE);
        _restartButton.setVisibility(View.GONE);
        _changeSizeButton.setVisibility(View.GONE);

        _winView.setVisibility(View.GONE);
    }

}