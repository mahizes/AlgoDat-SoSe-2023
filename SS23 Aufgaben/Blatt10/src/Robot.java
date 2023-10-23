import java.util.LinkedList;
import java.util.Queue;

public class Robot {
    protected int id;
    protected Position location;

    public Robot(int id, Position location) {
        this.id = id;
        this.location = location;
    }

    // copy constructor
    public Robot(Robot that) {
        id = that.id;
        location = new Position(that.location);
    }

    public void move(Move move) {
        location = move.endPosition;
    }

    public Queue<Move> validMoves(Board board) {
        Queue<Move> moves = new LinkedList<>();
        for (int dir = 0; dir < 4; dir++) {
            Move move = new Move(board, id, dir);
            if (!location.equals(move.endPosition)) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "R" + id + "@" + location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        // There is no need to compare the 'id' this since coincides with the index in the robot array
        // and robots are compared one-by-one using those indices.
        return location.equals(robot.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}

