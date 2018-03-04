import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] thresholds;
    private double mean = -1;
    private double stddev = -1;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                percolation.open(row, col);
            }

            thresholds[i] = percolation.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == -1) {
            mean = StdStats.mean(thresholds);
        }

        return mean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == -1) {
            stddev = StdStats.stddev(thresholds);
        }

        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(thresholds.length);
    }   

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(thresholds.length);
    }   
 
    // test client (described below)
    public static void main(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException();
        }

        int gridSize = 0;
        int trials = 0;

        try {
            gridSize = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);   
        } finally {}

        PercolationStats stats = new PercolationStats(gridSize, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() +", " + stats.confidenceHi() + "]");
    }
 }