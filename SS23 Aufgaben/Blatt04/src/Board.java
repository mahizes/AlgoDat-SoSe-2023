import java.util.InputMismatchException;
import java.util.Stack;

import static java.lang.Math.abs;
import java.util.ArrayList;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    private int[][] grid;
    private int availableSpaces;
    
    
    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n)
    {
        validateBoardSize(n);
        this.n = n;
        this.grid = initializeBoard(n);
        this.availableSpaces = calculateavailableSpaces(n);

    }

    private void validateBoardSize(int n) {
        if (n < 1 || n > 10) {
            throw new InputMismatchException("Invalid board size. Size should be between 1 and 10.");
        }
    }
    
    private int[][] initializeBoard(int n) {
        return new int[n][n];
    }
    
    private int calculateavailableSpaces(int n) {
        return n * n;
    }
    
    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }
    
    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        int count = 0;
        for(int[] row : grid) {
            for(int field : row) {
                if(field == 0) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        if (!isValidPosition(pos)) {
            throw new InputMismatchException("Invalid position. Position should be within the board's boundaries.");
        }
        return grid[pos.x][pos.y];
    }

    private boolean isValidPosition(Position pos) {
        return !(pos.x < 0 || pos.x >= n || pos.y < 0 || pos.y >= n);
    }

    /**
     *  Sets the specified token at Position pos.
     */    
    public void setField(Position pos, int token) throws InputMismatchException
    {
        if (!isValidToken(token)) {
            throw new InputMismatchException("Invalid token value. Expected -1, 0 or 1.");
        }
        if (!isValidPosition(pos)) {
            throw new InputMismatchException("Given position is outside of the board's boundaries.");
        }
        grid[pos.x][pos.y] = token;
    }

    private boolean isValidToken(int token) {
        return !(token < -1 || token > 1);
    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {
        if (!isPositionAvailable(pos)) {
            throw new InputMismatchException("The specified position is not free.");
        }
        setField(pos, player);
        availableSpaces--;
    }

    private boolean isPositionAvailable(Position pos) {
        return getField(pos) == 0;
    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {
        if (!hasMoveBeenMadeAtPosition(pos)) {
            throw new InputMismatchException("There's no move to reverse at the given position.");
        }
        setField(pos, 0);
        availableSpaces++;
    }

    private boolean hasMoveBeenMadeAtPosition(Position pos) {
        return getField(pos) != 0;
    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        // Check rows and columns
        for(int i = 0; i < n; i++) {
            if(checkLine(grid[i]) || checkLine(getColumn(i))) {
                return true;
            }
        }
        // Check diagonals
        return checkLine(getMainDiagonal()) || checkLine(getSecondaryDiagonal());
    }

    private boolean checkLine(int[] line) {
        int first = line[0];
        if(first == 0) {
            return false;
        }
        for(int i = 1; i < n; i++) {
            if(line[i] != first) {
                return false;
            }
        }
        return true;
    }

    private int[] getColumn(int index) {
        int[] column = new int[n];
        for(int i = 0; i < n; i++) {
            column[i] = grid[i][index];
        }
        return column;
    }

    private int[] getMainDiagonal() {
        int[] diagonal = new int[n];
        for(int i = 0; i < n; i++) {
            diagonal[i] = grid[i][i];
        }
        return diagonal;
    }

    private int[] getSecondaryDiagonal() {
        int[] diagonal = new int[n];
        for(int i = 0; i < n; i++) {
            diagonal[i] = grid[i][n - i - 1];
        }
        return diagonal;
    }

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        ArrayList<Position> validMoves = new ArrayList<>();
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(grid[row][col] == 0) {
                    validMoves.add(new Position(row, col));
                }
            }
        }
        return validMoves;
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(grid[i][j] == 0 ? "_" : (grid[i][j] == 1 ? "x" : "o"));
                if(j < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

