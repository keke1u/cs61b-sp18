package hw4.puzzle;

// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Solver {
    private class SearchNode {
        private WorldState state;
        private int moves;
        private SearchNode prevNode;
        private Integer priority;

        SearchNode(WorldState initial) {
            state = initial; // the initial WorldState
            moves = 0; // since no moves have been made yet
            prevNode = null; // since there is no previous search node
            priority = initial.estimatedDistanceToGoal();
        }

        SearchNode(WorldState state, SearchNode prevNode) {
            this.state = state;
            this.prevNode = prevNode;
            moves = prevNode.moves + 1;
            if (eDCaches.containsKey(this.state)) {
                priority = moves + eDCaches.get(this.state);
            } else {
                int ed = state.estimatedDistanceToGoal();
                priority = moves + ed;
                eDCaches.put(state, ed);
            }

        }

        public int priority() {
            return priority;
        }
    }

    public class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode x1, SearchNode x2) {
            return x1.priority.compareTo(x2.priority);
        }
    }

    private int numOfMoves;
    private Stack<WorldState> path;
    private Map<WorldState, Integer> eDCaches = new HashMap<>(); // Caches estimatedDistance
    /**
     * Constructor which solves the puzzle, computing
     *                  everything necessary for moves() and solution() to
     *                  not have to solve the problem again. Solves the
     *                  puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode currentNode = new SearchNode(initial);
        pq.insert(currentNode);
        path = new Stack<>();

        while (!currentNode.state.isGoal()) {
            for (WorldState neighbor : currentNode.state.neighbors()) {
                if (currentNode.prevNode == null || !neighbor.equals(currentNode.prevNode.state)) {
                    pq.insert(new SearchNode(neighbor, currentNode));
                }
            }
            currentNode = pq.delMin();
        }
        numOfMoves = currentNode.moves;
        for (SearchNode x = currentNode; x != null; x = x.prevNode) {
            path.push(x.state);
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     *                  at the initial WorldState.
     */
    public int moves() {
        return numOfMoves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     *                  to the solution.
     */
    public Iterable<WorldState> solution() {
        return path;
    }

}
