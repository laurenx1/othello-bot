package edu.caltech.cs2.project08;

import edu.caltech.cs2.project08.board.ArrayBoard;
import edu.caltech.cs2.project08.bots.AbstractSearcher;
import edu.caltech.cs2.project08.bots.AlphaBetaSearcher;
import edu.caltech.cs2.project08.bots.MinimaxSearcher;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.project08.game.Move;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("C")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchTests {

    public static final int MAX_DEPTH = 15;

    @Order(1)
    @ParameterizedTest(name = "Trace file {0}")
    @DisplayName("Test minimax")
    @CsvSource({
            "minimax_0.txt, 0",
            "minimax_1.txt, 1",
            "minimax_2.txt, 2",
            "minimax_3.txt, 3",
            "minimax_4.txt, 4",
            "minimax_5.txt, 5",
            "minimax_6.txt, 6",
            "minimax_7.txt, 7",
            "minimax_8.txt, 8",
            "minimax_9.txt, 9",
    })
    public void testMinimax(String traceFile, int randomSeed) throws IOException {
        Random r = new Random(randomSeed);
        // Load minimax leaf trace file
        Scanner s = new Scanner(new File("./tests/data/minimax/" + traceFile));
        List<String> ref = new ArrayList<>();
        while (s.hasNextLine()) {
            ref.add(s.nextLine());
        }
        String expectedBestMove = ref.remove(ref.size() - 1);
        ArrayBoard b = new ArrayBoard();
        // Go to random depth where the game hasn't ended
        int j = 0;
        int startDepth = r.nextInt(MAX_DEPTH);
        int ply = r.nextInt(5) + 2;
        while (j < startDepth) {
            IDeque<Move> moves = b.getMoves();
            int moveToMake = r.nextInt(moves.size());
            for (int k = 0; k < moveToMake; k++) {
                moves.removeFront();
            }
            b.makeMove(moves.removeFront());
            j++;
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
        Move actualBestMove = m.getBestMove(b, 0, 0);

        MatcherAssert.assertThat("Incorrect list of positions explored", e.positions,
                IsIterableContainingInOrder.contains(ref.toArray()));
        assertEquals(expectedBestMove, actualBestMove.toString(), "Returned move is not the best move");
    }

    @Order(2)
    @ParameterizedTest(name = "Trace file {0}")
    @DisplayName("Test alphabeta")
    @CsvSource({
            "alphabeta_0.txt, 10",
            "alphabeta_1.txt, 11",
            "alphabeta_2.txt, 12",
            "alphabeta_3.txt, 13",
            "alphabeta_4.txt, 14",
            "alphabeta_5.txt, 15",
            "alphabeta_6.txt, 16",
            "alphabeta_7.txt, 17",
            "alphabeta_8.txt, 18",
            "alphabeta_9.txt, 19",
    })
    public void testAlphaBeta(String traceFile, int randomSeed) throws IOException {
        Random r = new Random(randomSeed);
        // Load alphabeta leaf trace file
        Scanner s = new Scanner(new File("./tests/data/alphabeta/" + traceFile));
        List<String> ref = new ArrayList<>();
        while (s.hasNextLine()) {
            ref.add(s.nextLine());
        }
        String expectedBestMove = ref.remove(ref.size() - 1);
        ArrayBoard b = new ArrayBoard();
        // Go to random depth where the game hasn't ended
        int j = 0;
        int startDepth = r.nextInt(MAX_DEPTH);
        int ply = r.nextInt(5) + 2;
        while (j < startDepth) {
            IDeque<Move> moves = b.getMoves();
            int moveToMake = r.nextInt(moves.size());
            for (int k = 0; k < moveToMake; k++) {
                moves.removeFront();
            }
            b.makeMove(moves.removeFront());
            j++;
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
        Move actualBestMove = m.getBestMove(b, 0, 0);

        MatcherAssert.assertThat("Incorrect list of positions explored", e.positions,
                IsIterableContainingInOrder.contains(ref.toArray()));
        assertEquals(expectedBestMove, actualBestMove.toString(), "Returned move is not the best move");
    }
}
