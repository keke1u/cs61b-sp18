package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double mean;
    private double std;
    private double conLow;
    private double conHigh;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("The args of Stats should be positive!");
        }
        int[] data = new int[T];
        for (int i = 0; i < T; i += 1) {
            Percolation exp = pf.make(N);
            while (!exp.percolates()) {
                int position = StdRandom.uniform(N);
                exp.open(position / N, position % N);
            }
            if (exp.percolates()) {
                data[i] = exp.numberOfOpenSites();
            }
        }
        mean = StdStats.mean(data);
        std = StdStats.stddev(data);
        conLow = mean - 1.96 * std / Math.sqrt(T);
        conHigh = mean + 1.96 * std / Math.sqrt(T);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return conLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return conHigh;
    }

}
