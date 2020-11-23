package lab11.graphs;

import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound = false;
    private boolean flag = false;
    private int[] cameFrom;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // Your code here!
        // Set point where DFS starts
        cameFrom = new int[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            cameFrom[i] = Integer.MAX_VALUE;
        }
        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        cameFrom[s] = s;

        helperDFS(s);
        announce();
    }

    // Helper methods go here
    private void helperDFS(int v) {
        marked[v] = true;
        announce();

        if (cycleFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (marked[w] && cameFrom[v] != w) {
                cycleFound = true;
                cameFrom[w] = v;
                edgeTo[w] = v;
                helperDFS(w);
                if (cycleFound) {
                    return;
                }
            }
            if (!marked[w]) {
                cameFrom[w] = v;
                helperDFS(w);
                if (cycleFound) {
                    if (!flag) {
                        if (v != cameFrom[w]) {
                            flag = true; // mark to not draw the line
                            return;
                        }
                        edgeTo[w] = cameFrom[w]; // draw cycle lines
                        announce();
                        return;
                    }
                    return; // just recursion back after the cycle
                }
            }
        }
    }
}

