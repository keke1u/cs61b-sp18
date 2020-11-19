package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

// import java.util.Map;

public class Board implements WorldState {
    private static final int BLANK = 0;
    private int[][] tiles;
    private int size;
    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     */
    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i > size - 1 || j > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("Invalid index given: i =="
                    + i + "j ==" + j);
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return size;
    }

    /**
     * Returns the neighbors of the current board
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int currentRow = -1;
        int currentCol = -1;
        int n = size();

        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (tileAt(i, j) == BLANK) {
                    currentRow = i;
                    currentCol = j;
                }
            }
        }

        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                newTiles[i][j] = tileAt(i, j);
            }
        }

        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (Math.abs(currentRow - i) + Math.abs(currentCol - j) - 1 == 0) {
                    newTiles[currentRow][currentCol] = newTiles[i][j];
                    newTiles[i][j] = BLANK;
                    neighbors.enqueue(new Board(newTiles));
                    newTiles[i][j] = newTiles[currentRow][currentCol];
                    newTiles[currentRow][currentCol] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * Hamming estimate described below
     */
    public int hamming() {
        int row = -1;
        int col = -1;
        int value = -1;
        int sumError = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                value = tileAt(i, j);
                if (value == BLANK) {
                    continue;
                }
                row = (value - 1) / size;
                col = (value - 1) % size;
                if (row != i || col != j) {
                    sumError += 1;
                }
            }
        }
        return sumError;
    }

    /**
     * Manhatten estimate described below
     */
    public int manhatten() {
        int row = -1;
        int col = -1;
        int value = -1;
        int sumError = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                value = tileAt(i, j);
                if (value == BLANK) {
                    continue;
                }
                row = (value - 1) / size;
                col = (value - 1) % size;
                sumError = sumError + Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return sumError;
    }

    /**
     * Estimated distance to goal.
     * This method should simply return the results of manhaten()
     * when submitted to Gradescope.
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhatten();
    }

    /**
     * Returns true if this board's tile values
     * are the same position as y's
     * @param y
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board yBoard = (Board) y;
        if (this.size != yBoard.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i += 1) {
            for (int j = 0; j < this.size; j += 1) {
                if (this.tileAt(i, j) != yBoard.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Define hash code method to satisfy style checker
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /** Returns the string representation of the board.*/
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
