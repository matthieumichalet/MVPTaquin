package io.involvedapps.taquingame.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import io.involvedapps.taquingame.R;
import io.involvedapps.taquingame.model.Board;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private Board _board;
    private OnClickCell _onClickCell;

    public BoardAdapter(Board board, OnClickCell onClickCell) {
        _board = board;
        _onClickCell = onClickCell;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View newView = layoutInflater.inflate(R.layout.item_cell, parent, false);

        return new BoardViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        int currentCell = _board.getBoard().get(position);
        holder.bindView(currentCell, _onClickCell);
    }

    @Override
    public int getItemCount() {
        if (_board != null) {
            return _board.getBoard().size();
        } else {
            return 0;
        }
    }

    public void setBoard(Board board) {
        _board = board;
        notifyDataSetChanged();
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {

        private View _view;
        private CardView _cardview;
        private TextView _cellValueText;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            _view = itemView;
            _cardview = _view.findViewById(R.id.item_cardview);
            _cellValueText = _view.findViewById(R.id.item_cell_value);
        }

        public void bindView(int cellValue, OnClickCell onClickCell) {
            if (cellValue == -1) {
                _cellValueText.setText("");
                _view.setOnClickListener(null);
                _cardview.setVisibility(View.INVISIBLE);
            } else {
                _cellValueText.setText(String.valueOf(cellValue));
                _view.setOnClickListener(v -> onClickCell.click(cellValue));
                _cardview.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnClickCell {
        void click(int cellValue);
    }
}
