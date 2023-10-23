import java.util.LinkedList;

/**
 * ParitialSolution provides at least the functionality which is required
 * for the use in searching for solutions of the game in a search tree.
 * It can store a game situation (Board) and a sequence of mooves.
 */
public class PartialSolution {
    private Board board;
    private PartialSolution previous;
    private Move move;


    public PartialSolution(Board board) {
        this.board = board;
        this.previous = null;
        this.move = null;
    }

    public PartialSolution(Board board, PartialSolution previous, Move move) {
        this.board = board;
        this.previous = previous;
        this.move = move;
    }

    public Board getBoard() {
        return board;
    }

    public PartialSolution getPrevious() {
        return previous;
    }

    public Move getMove() {
        return move;
    }

    /**
     * Return the sequence of moves which resulted in this partial solution.
     *
     * @return The sequence of moves.
     */
    public LinkedList<Move> moveSequence() {
        LinkedList<Move> moves = new LinkedList<>();
        PartialSolution current = this;

        while (current != null) {
            if (current.getMove() != null) {
                moves.addFirst(current.getMove());
            }
            current = current.getPrevious();
        }

        return moves;
    }

    @Override
    public String toString() {
        String str = "";
        int lastRobot = -1;
        for (Move move : moveSequence()) {
            if (lastRobot == move.iRobot) {
                str += " -> " + move.endPosition;
            } else {
                if (lastRobot != -1) {
                    str += ", ";
                }
                str += "R" + move.iRobot + " -> " + move.endPosition;
            }
            lastRobot = move.iRobot;
        }
        return str;
    }
}

