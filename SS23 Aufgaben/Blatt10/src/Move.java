/**
 * Move formalizes the mvoe of a robot, specified by the index of the robot and the direction.
 * A move has only a specific meaning in the combination with a 'board' object. The current
 * location of the robot is taken from that board, and the end position of the move is
 * determined by taking into account the walls and positions of other robots on that board.
 */
public class Move {
    protected int iRobot;
    protected int dir;
    protected Position endPosition;

    // displacement of the 4 directions of movements (N, E, S, W order)
    private final static int[] xd = {0, 1, 0, -1};
    private final static int[] yd = {-1, 0, 1, 0};

    public Move(Board board, int iRobot, int dir) {
        this.iRobot = iRobot;
        this.dir = dir;
        this.endPosition = targetPosition(board);
    }

    // copy constructor
    public Move(Move that) {
        iRobot = that.iRobot;
        dir = that.dir;
        endPosition = new Position(that.endPosition);
    }

    /**
     * Determines the end position of a move starting at the specified position
     * in the specified direction. A movement ends as soon as a wall or a field
     * occupied by another robot is encountered.
     *
     * @param pos Start position of the movement
     * @return End position of the respective movement.
     */
    public Position nextPosition(Position pos) {
        Position nextPos = new Position(pos);
        nextPos.displace(xd[dir], yd[dir]);
        return nextPos;
    }

    public Position targetPosition(Board board) {
        Position pos = new Position(board.robots[iRobot].location);
        while (board.isPassable(pos, dir) && board.getField(nextPosition(pos)) == Board.EMPTY) {
            pos.displace(xd[dir], yd[dir]);
        }
        return pos;
    }

    @Override
    public String toString() {
        return "R" + iRobot + " -> " + endPosition;
    }
}

