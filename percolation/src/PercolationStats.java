import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] trialsGrid;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n et trials doivent être > 0");
        }

        this.trials = trials;
        this.trialsGrid = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }

            trialsGrid[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(trialsGrid);
    }

    public double stddev() {
        return StdStats.stddev(trialsGrid);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("il faut deux arguments");
        }

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
