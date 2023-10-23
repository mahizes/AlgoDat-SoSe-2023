/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
import java.util.ArrayList;

public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
    **/
    public static int alphaBeta(Board board, int player)
    {
        return evaluateGameState(board, player, -Integer.MAX_VALUE, Integer.MAX_VALUE); 
    }

    private static int evaluateGameState(Board board, int player, int alpha, int beta) {
        if (board.isGameWon()) {
            return -(board.nFreeFields() + 1);
        } else if (board.nFreeFields() == 0) {
            return 0;
        }
        for(Position currentMove : (ArrayList<Position>)board.validMoves()) {
            board.doMove(currentMove, player);
            int score = -evaluateGameState(board, -player, -beta, -alpha);
            board.undoMove(currentMove);
            if(score >= beta) {
                return beta;  // Beta cut-off
            }
            if(score > alpha) {
                alpha = score;
            }
        }
        return alpha;
    }
    
    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    {
        int[][] moveScores = new int[board.getN()][board.getN()];
        for(Position currentMove : (ArrayList<Position>)board.validMoves()) {
            board.doMove(currentMove, player);
            moveScores[currentMove.x][currentMove.y] = -evaluateGameState(board, -player, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            board.undoMove(currentMove);
        }
        printMoveScores(board, player, moveScores);
    }

    private static int[][] initializeMoveScores(Board board) {
        int[][] moveScores = new int[board.getN()][board.getN()];
        for(int i=0;i<board.getN();i++) {
            for(int j=0;j<board.getN();j++) {
                moveScores[i][j] = board.getField(new Position(i, j));
            }
        }
        return moveScores;
    }    

    public static void printMoveScores(Board board, int player, int[][] moveScores) {
        System.out.print("Evaluation for player");
        System.out.println(player == 1 ? " 'x':" : " 'o':");

        for(int i=0;i<board.getN();i++) {
            for(int j=0;j<board.getN();j++) {
                printMoveScore(moveScores[j][i]);
            }
            System.out.print("\n");
        }
    }

    private static void printMoveScore(int moveScore) {
        String[] scoreSymbols = {" x ", " o ", moveScore + " ", " " + moveScore + " "};
        int index = moveScore == 1 ? 0 : moveScore == -1 ? 1 : moveScore < 0 ? 2 : 3;
        System.out.print(scoreSymbols[index]);
    }

    public static void main(String[] args)
    {
    }
}

