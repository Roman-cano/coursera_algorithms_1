import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grille;
    private WeightedQuickUnionUF unionUf, unionUfNotBottom;
    private int topIndex;
    private int bottomIndex;
    private int numberOpen;
    private int size;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n doit être supérieur à 0");
        }
        grille = new boolean[n][n];
        topIndex = n * n;
        bottomIndex = n * n + 1;
        unionUf = new WeightedQuickUnionUF(n * n + 2);
        unionUfNotBottom = new WeightedQuickUnionUF(n * n + 1);
        size = grille.length;
        numberOpen = 0;
    }

    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("coordonnée incorecte");
        }

        if (isOpen(row, col)) {
            return;
        }
        grille[row - 1][col - 1] = true;
        numberOpen++;

        int index = (row - 1) * size + (col - 1);

        if (row == 1) {
            unionUf.union(index, topIndex);
            unionUfNotBottom.union(index, topIndex);
        }

        if (row == size) {
            unionUf.union(index, bottomIndex);
        }

        int[][] adjacent = {
                { -1, 0 },
                { 1, 0 },
                { 0, -1 },
                { 0, 1 }
        };

        for (int[] dir : adjacent) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 1 && newRow <= size && newCol >= 1 && newCol <= size && isOpen(newRow, newCol)) {
                int current = (newRow - 1) * size + (newCol - 1);
                unionUf.union(index, current);
                unionUfNotBottom.union(index, current);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("coordonnée incorecte");
        }
        return grille[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("coordonnée incorecte");
        }
        if (!isOpen(row, col)) {
            return false;
        }

        int index = (row - 1) * size + (col - 1);
        return unionUfNotBottom.find(index) == unionUfNotBottom.find(topIndex);
    }

    public int numberOfOpenSites() {
        return numberOpen;
    }

    public boolean percolates() {
        return unionUf.find(topIndex) == unionUf.find(bottomIndex);
    }

    private static void display(boolean[][] grille) {
        for (int i = 0; i < grille.length; i++) {
            for (int y = 0; y < grille.length; y++) {
                StdOut.print(grille[i][y] + " ");
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (n <= 0) {
            throw new IllegalArgumentException("veuillez saisir un nombre supérieur à 0");
        }

        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            perc.open(row, col);
        }

        display(perc.grille);
    }
}
