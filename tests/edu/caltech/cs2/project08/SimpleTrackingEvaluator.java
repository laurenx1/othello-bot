package edu.caltech.cs2.project08;

import edu.caltech.cs2.project08.board.ArrayBoard;
import edu.caltech.cs2.project08.game.Evaluator;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrackingEvaluator implements Evaluator<ArrayBoard> {
    public List<String> positions;

    public SimpleTrackingEvaluator() {
        this.positions = new ArrayList<>();
    }

    public int eval(ArrayBoard board) {
        positions.add(board.posString());
        return board.getScore() * (board.isBlackMove() ? 1 : -1);
    }
}