package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] data;
    private int trialTimes;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("The args of Stats should be positive!");
        }
        data = new double[T];
        trialTimes = T;
        for (int i = 0; i < T; i += 1) {
            Percolation exp = pf.make(N);
            while (!exp.percolates()) {
                int position = StdRandom.uniform(N * N);
                exp.open(position / N, position % N);
            }
            data[i] = exp.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(data);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(data);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(trialTimes);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(trialTimes);
    }

}
