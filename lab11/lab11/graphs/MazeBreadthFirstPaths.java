package lab11.graphs;

import java.util.Queue;
import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> q = new LinkedList<>();
        marked[s] = true;
        announce();
        q.add(s);

        if (s == t) {
            return;
        }

        while (!q.isEmpty()) {
            int v = q.remove();
            for (int n : maze.adj(v)) {
                if (!marked[n]) {
                    marked[n] = true;
                    edgeTo[n] = v;
                    distTo[n] = distTo[v] + 1;
                    announce();
                    if (n == t) {
                        return;
                    }
                    q.add(n);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

