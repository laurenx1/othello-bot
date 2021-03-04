package edu.caltech.cs2.project08;

import edu.caltech.cs2.project08.board.ArrayBoard;
import edu.caltech.cs2.project08.bots.AbstractSearcher;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.project08.game.Move;
import edu.caltech.cs2.project08.bots.MinimaxSearcher;
import edu.caltech.cs2.project08.bots.AlphaBetaSearcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class GenerateTestFiles {

    public static final int NUM_TEST_CASES = 10;
    public static final int MAX_DEPTH = 15;

    public static void saveIterableToOutput(Iterable<String> iter, String filename){
        try {
            Path file = Paths.get(filename);
            Files.write(file, iter, StandardCharsets.UTF_8);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Save minimax output
        for (int i = 0; i < NUM_TEST_CASES; i ++) {
            Random r = new Random(i);
            ArrayBoard b = new ArrayBoard();
            // Go to random depth where the game hasn't ended
            int j = 0;
            int startDepth = r.nextInt(MAX_DEPTH);
            int ply = r.nextInt(5) + 2;
            while (j < startDepth) {
                IDeque<Move> moves = b.getMoves();
                int moveToMake = r.nextInt(moves.size());
                for (int k = 0; k < moveToMake; k ++) {
                    moves.removeFront();
                }
                b.makeMove(moves.removeFront());
                j ++;
                // If the game has ended, try again
                if (b.isGameOver()) {
                    j = 0;
                    b = new ArrayBoard();
                }
            }

            SimpleTrackingEvaluator e = new SimpleTrackingEvaluator();
            AbstractSearcher<ArrayBoard> m = new MinimaxSearcher<>();
            m.setEvaluator(e);
            m.setDepth(ply);
            Move bestMove = m.getBestMove(b, 0, 0);
            e.positions.add(bestMove.toString());

            saveIterableToOutput(e.positions, String.format("./tests/data/minimax/minimax_%d.txt", i));
        }

        // Save alpha beta output
        for (int i = 0; i < NUM_TEST_CASES; i ++) {
            Random r = new Random(i + NUM_TEST_CASES);
            ArrayBoard b = new ArrayBoard();
            // Go to random depth where the game hasn't ended
            int j = 0;
            int startDepth = r.nextInt(MAX_DEPTH);
            int ply = r.nextInt(5) + 2;
            while (j < startDepth) {
                IDeque<Move> moves = b.getMoves();
                int moveToMake = r.nextInt(moves.size());
                for (int k = 0; k < moveToMake; k ++) {
                    moves.removeFront();
                }
                b.makeMove(moves.removeFront());
                j ++;
                // If the game has ended, try again
                if (b.isGameOver()) {
                    j = 0;
                    b = new ArrayBoard();
                }
            }

            SimpleTrackingEvaluator e = new SimpleTrackingEvaluator();
            AbstractSearcher<ArrayBoard> m = new AlphaBetaSearcher<>();
            m.setEvaluator(e);
            m.setDepth(ply);
            Move bestMove = m.getBestMove(b, 0, 0);
            e.positions.add(bestMove.toString());

            saveIterableToOutput(e.positions, String.format("./tests/data/alphabeta/alphabeta_%d.txt", i));
        }
    }
}