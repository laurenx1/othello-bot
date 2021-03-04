package play;

import edu.caltech.cs2.project08.board.ArrayBoard;
import edu.caltech.cs2.project08.board.ArrayBoardFactory;
import edu.caltech.cs2.project08.bots.AbstractSearcher;
import edu.caltech.cs2.project08.bots.AlphaBetaSearcher;
import edu.caltech.cs2.project08.game.BoardFactory;
import edu.caltech.cs2.project08.game.Evaluator;
import edu.caltech.cs2.project08.game.Move;
import edu.caltech.cs2.project08.game.SimpleEvaluator;

public class Bot {
    public static final String BOT_NAME = "MyBotName";
    public static final String BOT_PASS = "MyBotPassword";

    private Evaluator<ArrayBoard> evaluator;
    private BoardFactory<ArrayBoard> boardFactory;
    private AbstractSearcher<ArrayBoard> searcher;

    public Bot() {
        // TODO: Set up your evaluator and searcher here.
        evaluator = new SimpleEvaluator<>();
        boardFactory = new ArrayBoardFactory();

        // Make sure you set all of the necessary attributes on your searcher!
        searcher = new AlphaBetaSearcher<>();
        searcher.setDepth(8);
        searcher.setEvaluator(evaluator);
    }

    public Move getBestMove(String position, int myTime, int opTime) {
        boardFactory.setPosition(position);
        ArrayBoard board = boardFactory.create();
        return searcher.getBestMove(board, myTime, opTime);
    }

}
