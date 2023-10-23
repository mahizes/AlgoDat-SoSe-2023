import java.io.FileInputStream;
import java.util.*;

/**
 * The Board class represents one particular sitaution in the Ricochet Robots game.
 * The only changing parts during the game are the robts, while
 * other components are assumed to be static.
 */
public class Board {
    private int nx;
    private int ny;
    private static boolean[][] hwall;
    private static boolean[][] vwall;
    private int nRobots;
    protected Robot[] robots;
    protected int targetRobot;
    private Position targetPosition;
    protected static final int OFF = -2;
    protected static final int EMPTY = -1;

    /**
     * Constructor for a board of the Ricochet Robots game.
     * An empty board just with outer wall is created, and an empty list of robots.
     *
     * @param nx Width of the board
     * @param ny Height of the board
     * @param nRobots Number of robots that will be  placed on the board
     */
    public Board(int nx, int ny, int nRobots) {
        this.nx = nx;
        this.ny = ny;
        this.nRobots = nRobots;
        robots = new Robot[nRobots];
        hwall = new boolean[nx][ny + 1];
        vwall = new boolean[nx + 1][ny];
        // add outer boarder:
        for (int x = 0; x < nx; x++) {
            hwall[x][0] = true;
            hwall[x][ny] = true;
        }
        for (int y = 0; y < ny; y++) {
            vwall[0][y] = true;
            vwall[nx][y] = true;
        }
    }

    /**
     * Read a game situation from text file (or other input stream). First line is
     *     nx ny nRobots
     * All subsequent lines start with an (x, y) position and then specify
     *   1) a wall in a certain direction of the position (N, E, S, W)
     *   2) a robot on that position (R0, R1, ...)
     *   3) a target for a robot (T0, T1, ...)
     * Only one target is considered. If more than one target is specified,
     * later targets overwrite earlier declarations (even if it was for a
     * different robot - this game does not consider multiple targets).
     * Examples
     *   2 3 E
     * puts a wall 'east' of position (2, 3)
     *   4 0 R1
     * puts robot with id #1 on position (4, 0)
     *   3 1 T0
     * declares that robot #0 is the designated robot that needs to reach
     * the target, and that the target is on position (3, 1).
     * Outer walls around the board are always added.
     *
     * @param in Input stream from which the board is read.
     */
    public Board(Scanner in) {
        this(in.nextInt(), in.nextInt(), in.nextInt());
        while (in.hasNext()) {
            int x = in.nextInt();
            int y = in.nextInt();
            Scanner borders = new Scanner(in.nextLine());
            while (borders.hasNext()) {
                String str = borders.next();
                switch (str.charAt(0)) {
                    case 'N':
                        hwall[x][y] = true;
                        break;
                    case 'E':
                        vwall[x + 1][y] = true;
                        break;
                    case 'S':
                        hwall[x][y + 1] = true;
                        break;
                    case 'W':
                        vwall[x][y] = true;
                        break;
                    case 'R':
                        int r = Integer.parseInt(str.substring(1));
                        robots[r] = new Robot(r, new Position(x, y));
                        break;
                    case 'T':
                        targetRobot = Integer.parseInt(str.substring(1));
                        targetPosition = new Position(x, y);
                        break;
                }
            }
        }
    }

    /**
     * Copy constructor. Make a deep copy of the array of robots and copy
     * other object variable. The wall are class variables (static) and are
     * therefore not copied.
     *
     * @param that Board object that should get copied
     */
    public Board(Board that) {
        nx = that.nx;
        ny = that.ny;
        targetRobot = that.targetRobot;
        targetPosition = new Position(that.targetPosition);  // kind of static, no need to copy here
        nRobots = that.nRobots;
        robots = new Robot[nRobots];
        for (int n = 0; n < nRobots; n++) {
            robots[n] = new Robot(that.robots[n]);
        }
    }

    /**
     * Check whether the way from a given position is (in priciple) free
     * in a given direction. This method checks only for blocking by walls,
     * not whether the next field in that direction is free or blocked by
     * a robot.
     *
     * @param pos Start of the move
     * @param dir Direction of the move
     * @return {@code true} if the move is feasible, i.e. not blocked by a wall
     */
    public boolean isPassable(Position pos, int dir) {
        switch (dir) {
            case 0:
                return !hwall[pos.x][pos.y];
            case 1:
                return !vwall[pos.x + 1][pos.y];
            case 2:
                return !hwall[pos.x][pos.y + 1];
            case 3:
                return !vwall[pos.x][pos.y];
            default:
                return false;
        }
    }

    /**
     * Check whether the designated robot has reached its target position.
     *
     * @return {@code true} if robot reached the target
     */
    public boolean targetReached() {
        return targetPosition.equals(robots[targetRobot].location);
    }

    /**
     * Return whether and which robot is on a given field.
     *
     * @param pos Position of the field to be checked
     * @return id of the robot if any, otherwise EMPTY or OFF
     */
    public int getField(Position pos) {
        if (pos.x < 0 && pos.x >= nx && pos.y < 0 && pos.y >= ny) {
            return OFF;
        }
        for (Robot robot : robots) {
            if (pos.equals(robot.location)) {
                return robot.id;
            }
        }
        return EMPTY;
    }

    /**
     * Perform a move on the board.
     *
     * @param move The move to be performed
     */
    public void doMove(Move move) {
        robots[move.iRobot].move(move);
    }

    /**
     * Provide a list (as Queue) of possible moves in the current game
     * situation.
     *
     * @return Queue of possible moves
     */
    public Queue<Move> validMoves() {
        Queue<Move> moves = new LinkedList<>();
        for (Robot robot : robots) {
            moves.addAll(robot.validMoves(this));
        }
        return moves;
    }

    /**
     * Print the board in graphic representation
     */
    public void print() {
        if (targetReached()) {
            System.out.println("Robot #" + targetRobot + " is in the target position.");
        } else if (targetRobot != 0) {
            System.out.println("Robot #" + targetRobot + " needs to be moved to the target T.");
        }
        for (int y = 0; y <= ny; y++) {
            if (y == 0 || y == ny) {
                String line = "+";
                for (int x = 0; x < nx; x++) {
                    line += "----";
                }
                System.out.println(line.substring(0, line.length() - 1) + "+");
            } else {
                String line = "|";
                for (int x = 0; x < nx; x++) {
                    line += (hwall[x][y] ? "---" : "   ") + "+";    // "\u00b7";
                }
                System.out.println(line.substring(0, line.length() - 1) + "|");
            }
            if (y == ny) break;
            String line = "";
            for (int x = 0; x < nx; x++) {
                line += vwall[x][y] ? "|" : " ";
                Position pos = new Position(x, y);
                int token = getField(pos);
                boolean isTarget = pos.equals(targetPosition);
                line += isTarget ? " T " : (token == EMPTY) ? "   " : " " + token + " ";
            }
            System.out.println(line + "|");
        }
    }

    /**
     * Sparse string representation of a game situation, just listing the robots
     * and their positions. Use board.print() for richer information.
     *
     * @return The string representing the current state of the game
     */
    @Override
    public String toString() {
        String str = "";
        for (Robot robot : robots) {
            str += robot + "  ";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        /* TODO */
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return nx == board.nx && ny == board.ny && Arrays.equals(robots, board.robots);
    }

    @Override
    public int hashCode() {
        /* TODO */
        int result = Objects.hash(nx, ny);
        result = 31 * result + Arrays.hashCode(robots);
        return result;
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
        System.setIn(new FileInputStream("samples/rrBoard-sample03.txt"));
        Board board = new Board(new Scanner(System.in));
        board.print();
        Move move = new Move(board, 0, 0);
        System.out.println("Move: " + move);
        board.doMove(move);
        board.print();
        move = new Move(board, 0, 1);
        System.out.println("Move: " + move);
        board.doMove(move);
        board.print();
        move = new Move(board, 1, 1);
        System.out.println("Move: " + move);
        board.doMove(move);
        board.print();
        move = new Move(board, 1, 0);
        System.out.println("Move: " + move);
        board.doMove(move);
        board.print();
    }
}

