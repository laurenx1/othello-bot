package edu.caltech.cs2.project08.bots;

import edu.caltech.cs2.project08.game.Board;
import edu.caltech.cs2.project08.game.Evaluator;
import edu.caltech.cs2.project08.game.Move;

public class AlphaBetaSearcher<B extends Board> extends AbstractSearcher<B> {
    @Override
    public Move getBestMove(B board, int myTime, int opTime) {
        BestMove best = alphaBeta(this.evaluator, board, ply,Integer.MIN_VALUE+1, Integer.MAX_VALUE);
        return best.move;
    }

    private static <B extends Board> BestMove alphaBeta(Evaluator<B> evaluator, B board, int depth, int alpha, int beta) {
        if (depth == 0 || board.isGameOver()) {
            return new BestMove(evaluator.eval(board));
        }

        BestMove a = new BestMove(alpha);
        for (Move currMove : board.getMoves()) {
            board.makeMove(currMove);
            BestMove inQuestion = alphaBeta(evaluator, board, depth-1, -beta, -alpha);
            board.undoMove();
            if (-inQuestion.score > alpha) {
                alpha = -inQuestion.score;
                a.move = currMove;
                a.score = alpha;
            }

            if (alpha >= beta) {
                // alpha.score = -alpha.score;
                return a;
            }
        }
        return a;
    }
}