package edu.caltech.cs2.project08.bots;

import edu.caltech.cs2.project08.game.Board;
import edu.caltech.cs2.project08.game.Evaluator;
import edu.caltech.cs2.project08.game.Move;

public class MinimaxSearcher<B extends Board> extends AbstractSearcher<B> {
    @Override
    public Move getBestMove(B board, int myTime, int opTime) {
        BestMove best = minimax(this.evaluator, board, ply);
        return best.move;
    }

    private static <B extends Board> BestMove minimax(Evaluator<B> evaluator, B board, int depth) {
        if (depth == 0 || board.isGameOver()) { // add if the depth is 0 here?
            return new BestMove(evaluator.eval(board));
        }
        BestMove best = new BestMove(Integer.MIN_VALUE + 1);
        for (Move currMove : board.getMoves()) {
            board.makeMove(currMove);
            BestMove inQuestion = minimax(evaluator, board, depth-1);
            board.undoMove();
            if (-inQuestion.score > best.score) {
                best = inQuestion;
                best.move = currMove;
                best.score = -best.score;
            }
        }
        return best;
    }
}