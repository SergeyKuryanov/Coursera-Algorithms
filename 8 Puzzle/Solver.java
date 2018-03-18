import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final SearchNode solutionNode;

    private class SearchNode implements Comparable<SearchNode> {
        public final Board board;
        public final SearchNode predecessor;
        public final int moves;
        public final int priority;

        public SearchNode(Board board, SearchNode predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = predecessor == null ? 0 : predecessor.moves + 1;
            this.priority = board.manhattan() + moves;

        }

        public void insertNeighbors(MinPQ<SearchNode> priorityQueue) {
            for (Board neighbor : board.neighbors()) {
                if (predecessor != null && neighbor.equals(predecessor.board)) continue;

                SearchNode node = new SearchNode(neighbor, this);
                priorityQueue.insert(node);
            }
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> solutionPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();

        SearchNode initialNode = new SearchNode(initial, null);
        solutionPQ.insert(initialNode);

        SearchNode initialTwinNode = new SearchNode(initial.twin(), null);
        twinPQ.insert(initialTwinNode);

        while (true) {
            SearchNode solutionNode = solutionPQ.delMin();
            SearchNode twinNode = twinPQ.delMin();

            if (solutionNode.board.isGoal()) {
                this.solutionNode = solutionNode;
                break;
            } else if (twinNode.board.isGoal()) {
                this.solutionNode = null;
                break;
            }

            solutionNode.insertNeighbors(solutionPQ);
            twinNode.insertNeighbors(twinPQ);
        }
    }

    public boolean isSolvable() {
        return solutionNode != null;
    }

    public int moves() {
        if (isSolvable()) {
            return solutionNode.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() { 
        if (isSolvable()) {
            Deque<Board> solution = new LinkedList<>();
            SearchNode nextNode = solutionNode;
            while (nextNode != null) {
                solution.addFirst(nextNode.board);
                nextNode = nextNode.predecessor;
            }
            
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}