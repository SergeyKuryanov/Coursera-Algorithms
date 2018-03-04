import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int topRoot = 0;
    private int bottomRoot = 0;
    private int openSites = 0;
    
    private int gridDimension;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF fullSites;
    private boolean [][] sites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        gridDimension = n;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        fullSites = new WeightedQuickUnionUF(n * n + 1);
        topRoot = n * n;
        bottomRoot = n * n + 1;
        sites = new boolean[n][n];
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);

        if (isOpen(row, col)) return;

        int index = index(row, col);

        // Top
        if (row == 1) {
            unionFind.union(index, topRoot);
            fullSites.union(index, topRoot);
        } else {
            int topRow = row - 1;
            unionIfOpen(topRow, col, index);
        }

        // Right
        if (col < gridDimension) {
            int rightCol = col + 1;
            unionIfOpen(row, rightCol, index);
        }

        // Bottom
        if (row == gridDimension) {
            unionFind.union(index, bottomRoot);
        } else {
            int bottomRow = row + 1;
            unionIfOpen(bottomRow, col, index);
        }

        // Left
        if (col > 1) {
            int leftCol = col - 1;
            unionIfOpen(row, leftCol, index);
        }

        sites[row - 1][col - 1] = true;
        openSites++;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row, col);

        return sites[row - 1][col - 1] == true;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);

        int index = index(row, col);
        return fullSites.connected(index, topRoot);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(topRoot, bottomRoot);
    }

    // Helpers
    private int index(int row, int col) {
        return gridDimension * (row - 1) + (col - 1);
    }

    private void unionIfOpen(int row, int col, int indexToUnion) {
        if (isOpen(row, col)) {
            int index = index(row, col);
            unionFind.union(index, indexToUnion);
            fullSites.union(index, indexToUnion);
        }
    }

    private void check(int row, int col) {
        if (row < 1 || row > gridDimension || col < 1 || col > gridDimension) {
            throw new IllegalArgumentException();
        }
    }
}