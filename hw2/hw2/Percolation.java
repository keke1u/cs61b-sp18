package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridAntiBackWash; // grid without downside virtual nodes
    private int size;
    private boolean[] gridData;
    private int topVirtual;
    private int downVirtual;
    private int numOfOpen;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("The grid size should not be negative!");
        }
        grid = new WeightedQuickUnionUF(N * N + 2);
        gridAntiBackWash = new WeightedQuickUnionUF(N * N + 1);
        topVirtual = N * N;
        downVirtual = N * N + 1;
        size = N;
        numOfOpen = 0;
        gridData = new boolean[N * N];
        for (int i = 0; i < N * N; i += 1) {
            gridData[i] = false;
        }
        for (int i = 0; i < N; i += 1) {
            grid.union(topVirtual, xyTo1D(0, i));
            gridAntiBackWash.union(topVirtual, xyTo1D(0, i));
            grid.union(downVirtual, xyTo1D(N - 1, i));
        }
    }

    // transform the coordinates
    private int xyTo1D(int r, int c) {
        return size * r + c;
    }

    private void checkRange(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("The number is out of grid size!");
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRange(row, col);
        if (!gridData[xyTo1D(row, col)]) {
            gridData[xyTo1D(row, col)] = true;
            numOfOpen += 1;
        }

        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            grid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            gridAntiBackWash.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (row + 1 < size && isOpen(row + 1, col)) {
            grid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            gridAntiBackWash.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            grid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            gridAntiBackWash.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (col + 1 < size && isOpen(row, col + 1)) {
            grid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            gridAntiBackWash.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return gridData[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return isOpen(row, col) && gridAntiBackWash.connected(topVirtual, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }
    // does the system percolate?
    public boolean percolates() {
        return grid.connected(topVirtual, downVirtual);
    }
    // use for unit testing
    public static void main(String[] args) {
    }

}
